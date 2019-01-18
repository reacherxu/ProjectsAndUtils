package com.sap.projectAndUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parse the BPMN Xml content to a sequential model
 * 
 * @author richard.xu03@sap.com
 * @version $Id: XmlParser.java, v 0.1 Aug 27, 2018 3:47:17 PM richard.xu Exp $
 */
public class XmlParser {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** root document */
    protected Document root;

    /** all nodes in the process flow */
    protected List<Node> allNodes = new ArrayList<Node>();

    /** all the components in the process flow */
    protected List<Node> components = new ArrayList<Node>();

    /** an ordered components in the process flow */
    private List<Node> orderedCmpts = new ArrayList<Node>();

    /** expressions in the process flow */
    private List<Node> expressions = new ArrayList<Node>();

    private List<Node> nextNodeIsEnd = new ArrayList<Node>();

    /** process label */
    public static final String BPMN2_PORCESS = "//bpmn2:process";

    /** process label */
    public static final String BPMN2_SERVICE = "bpmn2:service";
    /** startEvent label */
    public static final String BPMN2_START_EVENT = "bpmn2:startEvent";
    /** endEvent label */
    public static final String BPMN2_END_EVENT = "bpmn2:endEvent";
    /** sequence label */
    public static final String BPMN2_SEQUENCE = "bpmn2:sequence";
    /** lines between entities */
    public static final String BPMN2_SEQUENCE_FLOW = "bpmn2:sequenceFlow";
    /** expressions */
    public static final String BPMN2_EXCLUSIVE_GATEWAY = "bpmn2:exclusiveGateway";

    /** incoming entity */
    public static final String BPMN2_INCOMING = "bpmn2:incoming";
    /** outgoing entity */
    public static final String BPMN2_OUTGOING = "bpmn2:outgoing";

    /** relative path */
    public static final String CURRENT_LOCATION = "./";

    /** attribute : id */
    public static final String NODE_ID = "id";
    /** attribute : name */
    public static final String NODE_NAME = "name";
    /** attribute : pId */
    public static final String NODE_PID = "pId";
    /** attribute : TrueOrFalse */
    public static final String CONDITION = "TrueOrFalse";

    /**
     * create instance
     * <ol>
     * <li>get all the nodes as well as components in the process flow</li>
     * <li>traverse the xml and get all the ordered components</li>
     * </ol>
     * 
     * @param xmlContent xml text
     * @return
     */
    public Document getInstance(String xmlContent) {

        if (StringUtils.isBlank(xmlContent) || StringUtils.equals("None", xmlContent)) {
            logger.error("BPMN Xml File Cannot Be Null");
            // throw new MODRuntimeException("BPMN Xml File Cannot Be Null",
            // ErrorCode.ERR_PARSE_BPMN, ErrorType.MESSAGE);
        }

        try {
            // 1. get all the nodes in the process flow
            this.root = DocumentHelper.parseText(xmlContent);

            Node processNode = getProcessNode();

            Node startNode = getStartNode(processNode);
            this.allNodes.add(startNode);

            List<Node> sequences = getSequenceNodes(processNode);
            this.allNodes.addAll(sequences);

            List<Node> sequFlows = getSequenceFlowNodes(processNode);
            this.allNodes.addAll(sequFlows);

            List<Node> endNodes = getEndNode(processNode);
            this.allNodes.addAll(endNodes);

            List<Node> services = getServiceNodes(processNode);
            this.allNodes.addAll(services);

            List<Node> tmpExprs = getExpressionNodes(processNode);
            this.setExpressions(tmpExprs);
            this.allNodes.addAll(tmpExprs);

            // 2. set the components
            this.components.addAll(sequences);
            this.components.addAll(services);
            this.components.addAll(tmpExprs);

            this.setNextNodeIsEnd(getNextEnd());

            // 3. set order, and check the validity of the process flow
            Node initialNode = decideStartNode(startNode);
            if (null != initialNode) {
                traverse(initialNode);

                if (this.orderedCmpts.size() == 1 && this.orderedCmpts.contains(startNode)) {
                    logger.error("The sequence not contains any services or sub-sequences!");
                    // throw new MODRuntimeException("The sequence not contains any services or
                    // sub-sequences", ErrorCode.ERROR);
                }
            }
        } catch (DocumentException e) {
            logger.error(e.getMessage());
            // throw new MODRuntimeException(e.getMessage(), ErrorCode.ERROR);
        }

        return root;
    }

    /**
     * In case of there's no start node. For automation sequence,only have one incoming
     * 
     * @param startNode
     */
    private Node decideStartNode(Node startNode) {
        Node initialNode = null;

        if (startNode != null) {
            initialNode = startNode;
        } else {
            for (Node node : components) {
                List<String> incomings = getDirection(node).get(BPMN2_INCOMING);
                if (CollectionUtils.isEmpty(incomings)) {
                    initialNode = node;
                    break;
                }
            }
        }
        return initialNode;
    }

    /**
     * there's only one node bpmn2:process
     * 
     * @return
     */
    public Node getProcessNode() {

        return root.selectSingleNode(BPMN2_PORCESS);
    }

    /**
     * get node startEvent
     * 
     * @param processNode
     * @return
     */
    public Node getStartNode(Node processNode) {

        return processNode.selectSingleNode(CURRENT_LOCATION + BPMN2_START_EVENT);
    }

    public Node getStartNode() {

        return getProcessNode().selectSingleNode(CURRENT_LOCATION + BPMN2_START_EVENT);
    }

    /**
     * get node endEvent
     * 
     * @param processNode
     * @return
     */
    public List<Node> getEndNode(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_END_EVENT);
    }

    public List<Node> getEndNode() {

        return getProcessNode().selectNodes(CURRENT_LOCATION + BPMN2_END_EVENT);
    }

    /**
     * get process flows
     * 
     * @param processNode
     * @return
     */
    public List<Node> getSequenceNodes(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_SEQUENCE);
    }

    /**
     * get services
     * 
     * @param processNode
     * @return
     */
    public List<Node> getServiceNodes(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_SERVICE);
    }

    /**
     * get lines between process flows
     * 
     * @param processNode
     * @return
     */
    public List<Node> getSequenceFlowNodes(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_SEQUENCE_FLOW);
    }

    /**
     * get expresssions
     * 
     * @param processNode
     * @return
     */
    public List<Node> getExpressionNodes(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_EXCLUSIVE_GATEWAY);
    }

    /**
     * judge if it is an condition
     * 
     * @param node
     * @return
     */
    public Boolean isExpression(Node node) {

        return this.expressions.contains(node);
    }

    /**
     * get next true component
     * 
     * @param expression
     * @return
     */
    public Node getNextTrue(Node expression) {
        return getCondition(expression, Boolean.TRUE);
    }

    /**
     * get next false component
     * 
     * @param expression
     * @return
     */
    public Node getNextFalse(Node expression) {
        return getCondition(expression, Boolean.FALSE);
    }

    /**
     * get next component by condition note : current node should be an expression/ gateway
     * 
     * @param expression
     * @param condition true or false
     * @return
     */
    public Node getCondition(Node expression, boolean condition) {
        Map<String, List<String>> direction = getDirection(expression);
        List<String> outgoings = direction.get(BPMN2_OUTGOING);

        if (outgoings.isEmpty()) {
            return null;
        }

        for (String outgoing : outgoings) {
            Node sequenceFlow = getNodeById(this.allNodes, outgoing);
            if (StringUtils.equalsIgnoreCase(String.valueOf(condition), getAttrValue(sequenceFlow, CONDITION))) {

                Map<String, List<String>> nextCmpt = getDirection(sequenceFlow);
                String nextCmptId = nextCmpt.get(BPMN2_OUTGOING).get(0);
                return getNodeById(this.orderedCmpts, nextCmptId);
            }
        }

        return null;
    }

    public List<String> getInComingNodesValueOfEndNode() {
        List<String> result = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        for (Node node : this.getEndNode()) {
            List<Node> inComingNodes = node.selectNodes(CURRENT_LOCATION + BPMN2_INCOMING);
            nodes.addAll(inComingNodes);
        }
        for (Node incomingNode : nodes) {
            result.add(incomingNode == null ? null : incomingNode.getStringValue());
        }
        return result;
    }

    public List<Node> getNextEnd() {
        List<Node> result = new ArrayList<>();
        List<String> inComingNodesValueOfEndNode = getInComingNodesValueOfEndNode();
        for (Node node : components) {
            Map<String, List<String>> outgoingNodes = getDirection(node);
            List<String> outGoingNodeIds = outgoingNodes.get(BPMN2_OUTGOING);
            if (!outGoingNodeIds.isEmpty()) {
                // get node that it have edge with end node
                for (String id : outGoingNodeIds) {
                    if (inComingNodesValueOfEndNode.contains(id)) {
                        result.add(node);
                    }
                }
            } else {
                // get node that it havn't edge with any node(except end node)
                result.add(node);
            }
        }
        return result;
    }

    /**
     * get next condition node
     * 
     * @param node
     * @return
     */
    public Node getNextExpression(Node node) {
        if (isEnd(node)) {
            return null;
        }

        // current node is not an expression and next node is an expression
        if (this.orderedCmpts.contains(node) && !this.expressions.contains(node) && isExpression(getNextCmpt(node))) {
            return getNextCmpt(node);
        }

        return null;
    }

    /**
     * return next component without rotation
     * 
     * <li>end node, return null</li>
     * <li>expression, return first branch node</li>
     * <li>otherwise, return next node</li>
     * 
     * @param node
     * @return
     */
    public Node getNextCmpt(Node node) {
        int index = this.orderedCmpts.indexOf(node);

        return index >= this.orderedCmpts.size() - 1 ? null : this.orderedCmpts.get(index + 1);
    }

    /**
     * return next component within rotation and this node is not a condition
     * 
     * TODO : refactor with method getCondition
     * 
     * @param node
     * @return
     */
    public Node getNextCmptFromRotation(Node node) {
        Map<String, List<String>> direction = getDirection(node);
        List<String> outgoings = direction.get(BPMN2_OUTGOING);

        if (outgoings.isEmpty()) {
            return null;
        }

        for (String outgoing : outgoings) {
            Node sequenceFlow = getNodeById(this.allNodes, outgoing);

            Map<String, List<String>> nextCmpt = getDirection(sequenceFlow);
            String nextCmptId = nextCmpt.get(BPMN2_OUTGOING).get(0);
            return getNodeById(this.orderedCmpts, nextCmptId);
        }

        return null;
    }

    /**
     * get incoming and outgoing lines of one node.
     * <li>For automation sequence, has single incoming and multiple outcomings</li>
     * <li>For production process, has multiple incomings and multiple outcomings</li>
     * 
     * @param node
     * @return
     */
    public Map<String, List<String>> getDirection(Node node) {
        Map<String, List<String>> direction = new HashMap<String, List<String>>();
        List<String> incomingList = new ArrayList<String>();
        List<String> outgoingList = new ArrayList<String>();

        List<Node> incomingNodes = node.selectNodes(CURRENT_LOCATION + BPMN2_INCOMING);
        List<Node> outgoingNodes = node.selectNodes(CURRENT_LOCATION + BPMN2_OUTGOING);

        // this is an edge instead of a node
        if (incomingNodes.isEmpty() && outgoingNodes.isEmpty() && !components.contains(node)) {
            incomingList.add(node.selectSingleNode(CURRENT_LOCATION + "@sourceRef") == null ? null
                    : node.selectSingleNode(CURRENT_LOCATION + "@sourceRef").getStringValue());
            outgoingList.add(node.selectSingleNode(CURRENT_LOCATION + "@targetRef") == null ? null
                    : node.selectSingleNode(CURRENT_LOCATION + "@targetRef").getStringValue());

            direction.put(BPMN2_INCOMING, incomingList);
            direction.put(BPMN2_OUTGOING, outgoingList);
            return direction;
        }

        // otherwise this is a component
        for (Node incomingNode : incomingNodes) {
            incomingList.add(incomingNode == null ? null : incomingNode.getStringValue());
        }
        for (Node outGoingNode : outgoingNodes) {
            outgoingList.add(outGoingNode == null ? null : outGoingNode.getStringValue());
        }
        direction.put(BPMN2_INCOMING, incomingList);
        direction.put(BPMN2_OUTGOING, outgoingList);
        return direction;
    }

    /**
     * get the node where id equals target id
     * 
     * @param nodes
     * @param id
     * @return
     */
    public Node getNodeById(List<Node> nodes, String id) {
        for (Node targetNode : nodes) {
            Node attr = targetNode.selectSingleNode(CURRENT_LOCATION + "@" + NODE_ID);

            if (StringUtils.isNotEmpty(attr.getStringValue()) && StringUtils.equals(attr.getStringValue(), id)) {
                return targetNode;
            }
        }
        return null;
    }

    /**
     * get attr value of the node
     * 
     * @param node
     * @param attrName
     * @return
     */
    public String getAttrValue(Node node, String attrName) {
        if (node == null) {
            return null;
        }

        Node attribute = node.selectSingleNode(CURRENT_LOCATION + "@" + attrName);
        return attribute.getStringValue();
    }

    /**
     * get node's id
     * 
     * @param node
     * @return
     */
    public String getId(Node node) {
        return getAttrValue(node, NODE_ID);
    }

    /**
     * get node's name
     * 
     * @param node
     * @return
     */
    public String getName(Node node) {
        return getAttrValue(node, NODE_NAME);
    }

    /**
     * get node's pid
     * 
     * @param node
     * @return
     */
    public String getPId(Node node) {
        return getAttrValue(node, NODE_PID);
    }

    /**
     * judge if it is the end of the operational flow true if it is the end, otherwise false
     * 
     * @param endNode
     * @return
     */
    public Boolean isEnd(Node endNode) {
        Map<String, List<String>> direction = getDirection(endNode);
        return direction.get(BPMN2_OUTGOING).isEmpty();
    }

    /**
     * iteratively traverse flow and only save the components
     * 
     * @param node
     */
    private void traverse(Node node) {
        Map<String, List<String>> direction = getDirection(node);
        List<String> outgoingList = direction.get(BPMN2_OUTGOING);

        // in case of that there's no start node
        // so need to add node itself
        if (components.contains(node)) {
            this.orderedCmpts.add(node);
        }

        for (String id : outgoingList) {
            Node nextNode = getNodeById(this.getAllNodes(), id);

            if (!orderedCmpts.contains(nextNode)) {
                traverse(nextNode);
            }
        }
    }

    /**
     * Getter method for property <tt>allNodes</tt>.
     * 
     * @return property value of allNodes
     */
    public List<Node> getAllNodes() {
        return allNodes;
    }

    /**
     * Getter method for property <tt>components</tt>.
     * 
     * @return property value of components
     */
    public List<Node> getComponents() {
        return components;
    }

    /**
     * Getter method for property <tt>orderedCmpts</tt>.
     * 
     * @return property value of orderedCmpts
     */
    public List<Node> getOrderedCmpts() {
        return orderedCmpts;
    }

    /**
     * Getter method for property <tt>expressions</tt>.
     * 
     * @return property value of expressions
     */
    public List<Node> getExpressions() {
        return expressions;
    }

    /**
     * Setter method for property <tt>expressions</tt>.
     * 
     * @param expressions value to be assigned to property expressions
     */
    public void setExpressions(List<Node> expressions) {
        this.expressions = expressions;
    }

    public List<Node> getNextNodeIsEnd() {
        return nextNodeIsEnd;
    }

    public void setNextNodeIsEnd(List<Node> nextNodeIsEnd) {
        this.nextNodeIsEnd = nextNodeIsEnd;
    }

    /**
     * Getter method for property <tt>root</tt>.
     * 
     * @return property value of root
     */
    public Document getRoot() {
        return root;
    }

    /**
     * Setter method for property <tt>root</tt>.
     * 
     * @param root value to be assigned to property root
     */
    public void setRoot(Document root) {
        this.root = root;
    }

}
