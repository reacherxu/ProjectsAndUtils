package com.sap.projectAndUtils;

/**
 * SAP Inc. Copyright (c) 1972-2019 All Rights Reserved.
 */

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
import com.sap.cdt.domain.enums.ErrorCode;
import com.sap.cdt.domain.enums.ErrorType;
import com.sap.cdt.domain.enums.MODRuntimeException;

/**
 * Parse production process content. <br>
 * Multiple business service corresponds to multiple DME chains. <br>
 * <li>1.get all the nodes which have no incomings.</li>
 * <li>2.seperate into several chains.</li>
 * <li>3.build cache model.</li>
 * 
 * @author richard.xu03@sap.com
 * @version $Id: ProductionProcessParser.java, v 0.1 Jan 16, 2019 10:49:58 AM richard.xu Exp $
 */
public class ProductionProcessParser extends XmlParser {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Business service label, such as sfc complete */
    public static final String BPMN2_BIZ_SERVICE = "bpmn2:bizService";

    /** automation sequence label */
    public static final String BPMN2_AUTOMATION = "bpmn2:automation";

    /** extension service label, such as indicator write */
    public static final String BPMN2_EXTENSION = "bpmn2:extService";

    /** edge label */
    public static final String BPMN2_PP_FLOW = "bpmn2:ppFlow";

    /**
     * If has multiple business services, then has multiple chains.
     * <li>key is the ordered DME chain,value is the business service.</li>
     * <li>according to the value, we can decide the pre-extension or post-extension.</li>
     */
    private Map<DMEChain, Node> dmeChainMap = new HashMap<>();

    /**
     * One single chain for business service. Mainly for future design, currently not used.
     */
    private List<Node> bizServiceList = new ArrayList<>();

    private List<Node> bizServices = new ArrayList<>();

    private List<Node> extensions = new ArrayList<>();

    private List<Node> automationSeqs = new ArrayList<>();

    private List<Node> ppFlows = new ArrayList<>();

    @Override
    public Document getInstance(String xmlContent) {

        if (StringUtils.isBlank(xmlContent) || StringUtils.equals("None", xmlContent)) {
            logger.error("BPMN Xml File Cannot Be Null");
            throw new MODRuntimeException("BPMN Xml File Cannot Be Null", ErrorCode.ERR_PARSE_BPMN, ErrorType.MESSAGE);
        }

        try {
            // 1. get all the nodes in the production process
            this.root = DocumentHelper.parseText(xmlContent);

            Node processNode = getProcessNode();

            bizServices = getBizServiceNodes(processNode);
            this.allNodes.addAll(bizServices);

            extensions = getExtensionNodes(processNode);
            this.allNodes.addAll(extensions);

            automationSeqs = getAutomationSequenceNodes(processNode);
            this.allNodes.addAll(automationSeqs);

            ppFlows = getPPFlowNodes(processNode);
            this.allNodes.addAll(ppFlows);

            // 2. set the components
            this.components.addAll(bizServices);
            this.components.addAll(extensions);
            this.components.addAll(automationSeqs);

            // 3. set order, and check the validity of the production process
            getInitialNodes();
            for (DMEChain dmeChain : dmeChainMap.keySet()) {
                Node initialNode = dmeChainMap.get(dmeChain);
                traverse(initialNode, dmeChain);
            }

        } catch (DocumentException e) {
            logger.error(e.getMessage());
            throw new MODRuntimeException(e.getMessage(), ErrorCode.ERROR);
        }

        return root;
    }

    /**
     * <li>1.get all the nodes without an incoming edge.</li>
     * <li>2.initialize the DME chain map.</li>
     * 
     * @param object
     * @return
     */
    private List<Node> getInitialNodes() {
        List<Node> initialNodes = new ArrayList<>();

        // 1. get initial nodes
        // 1.1 if has pre-extensions
        for (Node node : components) {
            List<String> incomings = getDirection(node).get(BPMN2_INCOMING);
            if (CollectionUtils.isEmpty(incomings)) {
                initialNodes.add(node);
            }
        }
        // 1.2 if not has pre-extensions, then add business service
        if (initialNodes.size() < bizServices.size()) {
            for (Node node : ppFlows) {
                Map<String, List<String>> direction = getDirection(node);
                String incomingId = direction.get(BPMN2_INCOMING).get(0);
                Node incomingNode = getNodeById(allNodes, incomingId);
                String outcomingId = direction.get(BPMN2_OUTGOING).get(0);
                Node outgoingNode = getNodeById(allNodes, outcomingId);
                if ((!components.contains(incomingNode) || bizServices.contains(incomingNode)) && bizServices.contains(outgoingNode)) {
                    initialNodes.add(outgoingNode);
                }
            }
        }

        // 2. initialize chain map
        for (Node node : initialNodes) {
            DMEChain chain = new DMEChain(new ArrayList<>(), node);
            dmeChainMap.put(chain, node);
        }
        return initialNodes;
    }

    /**
     * if don't has the pre-extensions, then get the initial node via ppFlow
     * 
     * @param node pp flow
     * @return
     */
    private boolean isStartFlow(Node node) {


        return false;
    }

    /**
     * iteratively traverse the flow and only save the components
     * 
     * @param node
     * @param dmeChain
     */
    private void traverse(Node node, DMEChain dmeChain) {
        Map<String, List<String>> direction = getDirection(node);
        List<String> outgoingList = direction.get(BPMN2_OUTGOING);

        // in case of that there's no start node,so need to add node itself
        if (components.contains(node)) {
            dmeChain.getChain().add(node);
            if (isBizService(node)) { // note that only shoud add once
                Node originNode = dmeChainMap.get(dmeChain);
                dmeChainMap.replace(dmeChain, originNode, node);
                dmeChain.setBizService(node);
                dmeChain.setPos(dmeChain.getChain().size() - 1);
            }
        }

        for (String id : outgoingList) {
            Node nextNode = getNodeById(this.getAllNodes(), id);

            if (!dmeChain.getChain().contains(nextNode) && !isStopTraverse(dmeChain, nextNode)) {
                traverse(nextNode, dmeChain);
            }
        }
    }

    /**
     * if current and next node is a business service, will stop
     * <li></li>
     * <li></li>
     * 
     * @param dmeChain
     * @param nextNode
     * @return true if satisfy the condition
     */
    private boolean isStopTraverse(DMEChain dmeChain, Node nextNode) {
        boolean hasBizService = false;
        for (Node node : dmeChain.getChain()) {
            if (isBizService(node)) {
                hasBizService = true;
                break;
            }
        }

        return hasBizService && bizServices.contains(nextNode);
    }

    /**
     * judge if it is a business service
     * 
     * @param node
     * @return
     */
    private boolean isBizService(Node node) {

        return bizServices.contains(node);
    }

    /**
     * get nodes for DME business services
     * 
     * @param processNode
     * @return
     */
    public List<Node> getBizServiceNodes(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_BIZ_SERVICE);
    }

    /**
     * get nodes for automation sequences
     * 
     * @param processNode
     * @return
     */
    public List<Node> getAutomationSequenceNodes(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_AUTOMATION);
    }

    /**
     * get nodes for DME extensions
     * 
     * @param processNode
     * @return
     */
    public List<Node> getExtensionNodes(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_EXTENSION);
    }

    /**
     * get nodes for edges
     * 
     * @param processNode
     * @return
     */
    public List<Node> getPPFlowNodes(Node processNode) {

        return processNode.selectNodes(CURRENT_LOCATION + BPMN2_PP_FLOW);
    }

    /**
     * Getter method for property <tt>dmeChainMap</tt>.
     * 
     * @return property value of dmeChainMap
     */
    public Map<DMEChain, Node> getDmeChainMap() {
        return dmeChainMap;
    }

    /**
     * Setter method for property <tt>dmeChainMap</tt>.
     * 
     * @param dmeChainMap value to be assigned to property dmeChainMap
     */
    public void setDmeChainMap(Map<DMEChain, Node> dmeChainMap) {
        this.dmeChainMap = dmeChainMap;
    }

    /**
     * Getter method for property <tt>bizServiceList</tt>.
     * 
     * @return property value of bizServiceList
     */
    public List<Node> getBizServiceList() {
        return bizServiceList;
    }

    /**
     * Setter method for property <tt>bizServiceList</tt>.
     * 
     * @param bizServiceList value to be assigned to property bizServiceList
     */
    public void setBizServiceList(List<Node> bizServiceList) {
        this.bizServiceList = bizServiceList;
    }

    /**
     * Contains chain relative info
     * <li>current chain</li>
     * <li>start node</li>
     * <li>business service node</li>
     * <li>index of business service</li>
     */
    public class DMEChain {
        private ArrayList<Node> chain = new ArrayList<>();

        private Node startNode;

        private Node bizService;

        private int pos;

        public DMEChain() {

        }

        /**
         * @param chain
         * @param startNode
         */
        public DMEChain(ArrayList<Node> chain, Node startNode) {
            super();
            this.chain = chain;
            this.startNode = startNode;
        }

        /**
         * Getter method for property <tt>startNode</tt>.
         * 
         * @return property value of startNode
         */
        public Node getStartNode() {
            return startNode;
        }

        /**
         * Setter method for property <tt>startNode</tt>.
         * 
         * @param startNode value to be assigned to property startNode
         */
        public void setStartNode(Node startNode) {
            this.startNode = startNode;
        }

        /**
         * Getter method for property <tt>bizService</tt>.
         * 
         * @return property value of bizService
         */
        public Node getBizService() {
            return bizService;
        }

        /**
         * Setter method for property <tt>bizService</tt>.
         * 
         * @param bizService value to be assigned to property bizService
         */
        public void setBizService(Node bizService) {
            this.bizService = bizService;
        }

        /**
         * Getter method for property <tt>pos</tt>.
         * 
         * @return property value of pos
         */
        public int getPos() {
            return pos;
        }

        /**
         * Setter method for property <tt>pos</tt>.
         * 
         * @param pos value to be assigned to property pos
         */
        public void setPos(int pos) {
            this.pos = pos;
        }

        /**
         * Getter method for property <tt>chain</tt>.
         * 
         * @return property value of chain
         */
        public ArrayList<Node> getChain() {
            return chain;
        }

        /**
         * Setter method for property <tt>chain</tt>.
         * 
         * @param chain value to be assigned to property chain
         */
        public void setChain(ArrayList<Node> chain) {
            this.chain = chain;
        }
    }

}
