package com.sap.cdt.domain.enums;

/**
 * Enum class for error code
 */
public enum ErrorCode {

    /*
     * 2XXXX for Success.Messages
     */
    OK(20000, "ok", false),

    /*
     * 50XXX: Internal Exceptions
     */
    ERROR(50000, "error", true),

    /*
     * 51XXX: Connection related error
     */
    ERR_CONNECTION_FAILED(51001, "connectionFailed", true),

    /*
     * 52XXX: Authorization related error
     */

    /*
     * 40XXX: Shop flow related exceptions
     */
    ERR_PROJECT_CREATE_DUPLICATE(40001, "projectCreateDuplicateError", true),
    ERR_PROJECT_DELETE(40002, "projectDeleteError", true),
    ERR_PROJECT_UPDATE(40003, "projectUpdateError", true),
    ERR_PROJECT_UPDATE_NOT_FOUND(40004, "projectUpdateNotFoundError", true),
    ERR_PROJECT_GET(40005, "getProjectsError", true),
    ERR_PROJECT_NOT_FOUND(40006, "projectNotFound", true),
    ERR_PROJECT_STATUS_INVALID(40007, "projectStatusInvalid", true),
    ERR_PROJECT_NOT_AUTH(40008, "projectNotAuthError", true),
    ERR_PROJECT_SAVE(40009, "projectSaveError", true),
    ERR_PROJECT_DELETE_ACTIVE(40010, "activeProjectDeleteFailed", true),
    ERR_PROJECT_ACTIVE_MISS(40011, "activeProjectMissingFailed", true),
    ERR_PROJECT_ACTIVE_INVALID_STATUS(40012, "activeProjectStatusFailed", true),
    ERR_OPFFLOW_DUPLICATE_IN_PROJECT(400013, "opfDuplicateInProjectError", true),
    ERP_GET_MACHINES_BY_SHOPFLOOR(400014, "getMachinesByShopFloorError", true),
    ERP_GET_COMFLOWS_BY_SHOPFLOOR(400015, "getCommflowsByShopFloorError", true),
    ERR_PROJECT_CLONE(400016, "copyShopFloorError", true),
    ERR_SF_COPY_ITEMS(400017, "sfCopyItemsError", true),
    ERR_PROJECT_MACHINE_UNEXIST(400018, "sfMachinedUnexist", true),
    /*
     * 41XXX: Administration related exceptions(Incl. User, WorkGroup, DataSource...)
     */
    // User
    ERR_USER_GET(41001, "getUsersError", true),
    ERR_USERROLE_GET(41002, "getUserRolesError", true),
    ERR_USER_NOT_FOUND(41003, "userNotFoundError", true),
    ERR_USER_DELETE(41004, "deleteUserFailed", true),
    ERR_SYNC_UAA(41005, "syncUaaDataFailed", true),
    ERR_CURRENT_USERSCOPE_GET(41006, "getCurrentUserScopeError", true),
    ERR_USER_REMOVE_SLEF(41007, "removeSelfUserError", true),
    // Workgroup
    ERR_WORKGROUP_ASSIGN_USER(41007, "assignUsersToWorkGroupFailed", true),
    ERR_WORKGROUP_CREATE(41008, "createWorkGroupError", true),
    ERR_WORKGROUP_NOT_FOUND(41009, "workGroupNotFoundError", true),
    ERR_WORKGROUP_GET(41010, "getWorkGroupError", true),
    ERR_WORKGROUP_EXISTED(41011, "workgroupCreateExisted", true),
    ERR_WORKGROUP_DELETE(41012, "workGroupDeleteError", true),
    ERR_WORKGROUP_INUSE(41013, "workGroupInUse", true),
    // DataSource
    ERR_DATA_SERVER_DELETE(41014, "dataServerDeleteError", true),
    ERR_DATA_SERVER_EXISTED(41015, "dataServerCreateExisted", true),
    ERR_DATA_SERVER_INUSE(41016, "dataSourceInUse", true),
    ERR_DATA_SERVER_SAVE(41017, "dataSoureSaveError", true),
    ERR_DATA_SERVER_GET(41018, "dataSourceGetError", true),

    /*
     * 42XXX: Routing related exception
     */
    ERR_ROUTING_DELETE(42001, "routingDeleteError", true),
    ERR_ROUTING_SAVE(42002, "routingSaveError", true),
    ERR_ROUTING_GET(42003, "routingGetError", true),
    ERR_ROUTING_UPDATE_NOT_FOUND(42004, "routingUpdateNotFoundError", true),
    ERR_ROUTING_GET_LIST(42005, "getRoutingListError", true),
    ERR_ROUTING_GET_SITES(42006, "getMeSitesError", true),
    ERR_ROUTING_INUSE(42007, "routingInUse", true),
    ERR_MARKEDOPERATION_SAVE(42008, "markedOpSaveError", true),
    ERR_MARKEDOPERATION_DELTE(42009, "markedOpDeleteError", true),
    ERR_MARKEDOPERATION_GET(42010, "markedOpGetError", true),
    ERR_ROUTING_CLONE(42011, "routingCloneError", true),

    /*
     * 43XXX: Process flow related exceptions
     */
    ERR_OPFLOW_DUPLICATE(43001, "opfDuplicateError", true),
    ERR_OPFLOW_GET(43002, "getOpfsError", true),
    // ERR_OPFLOW_DELETE(43003, "opfDeleteError", true), not in use
    ERR_OPFLOW_NOT_FOUND(43004, "opfNotFoundError", true),
    ERR_OPFLOW_UPDATE(43005, "opfUpdateError", true),
    ERR_OPFFLOW_INUSE(43006, "opfInUse", true),
    ERR_OPF_VARIABLE_DUPLICATE(43007, "variableDuplicateError", true),
    ERR_OPF_VARIABLE_NOT_FOUND(43008, "variableNotFoundError", true),
    ERR_OPF_CPN_DUPLICATE(43009, "componentDuplicateError", true),
    ERR_OPF_CPN_NOT_FOUND(43010, "componentNotFoundError", true),
    ERR_PF_NOT_FOUND(43011, "sequenceNotFoundError", true),
    ERR_OPF_SEQ_DUPLICATE(43012, "sequenceDuplicateError", true),
    ERR_PF_SAVE(43013, "sequenceSaveError", true),
    ERR_OPFLOW_SAVE(43014, "operationFlowSaveError", true),
    ERR_OPF_VARIABLE_SAVE(43015, "variableSaveError", true),
    ERR_OPF_CPN_SAVE(43016, "componentSaveError", true),
    ERR_OPF_SEQ_ASSO_CP(43017, "sequenceAssociateCompletedOpfs", true),
    ERR_OPF_SEQ_ASSO_IP(43018, "sequenceAssociateInProcessOpfs", true),
    ERR_OPF_CPN_DELETE(43019, "componentDeleteError", true),
    ERR_OPF_SEQ_ASSO_SEQ(43020, "sequenceAssociateSeqs", true),
    ERR_OPF_CF_NOT_FOUND(43021, "bindCommunicationModelNotFoundError", true),
    ERR_OPF_CF_NOT_COMPLETE(43022, "bindCommunicationModelNotCompleteError", true),
    ERR_OPF_INVALID_SERVICE(43023, "operationalFlowInvalidError", true),
    ERR_OPFLOW_UPDATE_COMPLETED(43024, "operationFlowUpdateCompleted", true),
    ERR_OPFLOW_CLONE(43025, "operationFLowCloneError", true),
    ERR_PF_ASSO(43026, "sequenceAssociateOpfs", true),
    ERR_OPF_SEQ_DELETE(43027, "sequenceDeleteError", true),
    ERR_OPF_SEQ_DEL_SEQ(43028, "seqDeleteUsedInOtherSeqs", true),
    ERR_OPF_SEQ_ASSO_MF(43029, "sequenceAssociateMfs", true),
    ERR_OPF_SEQ_ASSO_MF_CP(43030, "sequenceAssociateCompletedMfs", true),
    ERR_OPF_SEQ_ASSO_MF_IP(43031, "sequenceAssociateInProcessMfs", true),
    ERR_PARAM_VALIDATION(43032, "paramValidationError", true),
    ERR_OPF_SEQ_EMPTY(43033, "sequenceIsEmpty", true),
    ERR_PARAM_INVALID(43034, "invalidParameterError", true),

    /*
     * 44XXX: Machine related exceptions
     */
    // ERR_BUILD_REQUEST_C_MULTI_CLIENT_PROXY(44001, "buildCreateMultiClientProxyReqError", true),
    ERR_CLIENT_PROXY_SAVE(44002, "clientProxySaveError", true),
    ERR_MACHINE_SAVE(44003, "saveMachinesError", true),
    ERR_MM_SERVICE_PROVIDER_FETCH(44004, "getServiceProviderFromMachineModelError", true),
    ERR_MM_EQUIPMENT_FETCH(44005, "getEquipmentFromMachineModelError", true),
    ERR_MM_PCO_FETCH(44006, "getPCoFromMachineModelError", true),
    ERR_MACHINE_NOT_FOUND(44007, "machineNotFound", true),
    ERR_MACHINE_IS_USED(44008, "machineIsUsed", true),
    ERR_MACHINES_CLONE(44009, "copyMachinesError", true),
    ERR_PCO_NOT_CONNECT(44010, "pcoNotConnect", true),
    ERR_MACHINE_INVALID_DELETE(44011, "deleteInvalidMachinesError", true),
    /*
     * 45XXX: Message flow related exceptions
     */
    ERR_MSGFLOW_DUPLICATE(45001, "messageFlowDuplicateError", true),
    ERR_MSGFLOW_NOT_FOUND(45002, "messageFlowNotFoundError", true),
    ERR_MSGFLOW_SAVE(45003, "messageFlowSaveError", true),
    ERR_MSGFLOW_GET(45004, "messageFlowGetError", true),
    ERR_MSGFLOW_DELETE(45005, "messageFlowDeleteError", true),
    ERR_MSGFLOW_SERVICE_SAVE(45006, "messageFlowServiceSaveError", true),
    ERR_MSGFLOW_NOTIFICATION_SAVE(45007, "messageFlowNotificationSaveError", true),
    ERR_MSGFLOW_NOTIFICATION_PARAM_SAVE(45008, "messageFlowNotificationParamSaveError", true),
    ERR_MSGFLOW_SEQ_NOT_BOUNDED(45009, "messageFlowSequenceNotBoundedError", true),
    ERR_MSGFLOW_CLONE(45010, "messageFlowCloneError", true),
    ERR_MSGFLOW_COMPLETED(45011, "messageFlowIsCompletedError", true),

    /*
     * 46XXX: Communication model related exceptions
     */
    ERR_CF_DUPLICATE(46003, "communicationModelDuplicateError", true),
    ERR_CF_SAVE(46004, "communicationModelSaveError", true),
    ERR_CF_GET(46005, "communicationModelGetError", true),
    ERR_CF_NOT_FOUND(46006, "communicationModelNotFoundError", true),
    ERR_CF_DELETE(46007, "communicationModelDeleteError", true),
    ERR_CF_CLONE(46008, "communicationModelCloneError", true),
    ERR_CF_COMPLETED(46009, "communicationModelIsCompletedError", true),
    ERR_CF_SP_GET(46010, "communicationModelServiceProviderGetError", true),
    ERR_CF_CPN_GET(46011, "communicationModelComponentGetError", true),
    ERR_CFCPN_SAVE(46012, "communicationModelComponentSaveError", true),
    ERR_CFPROVIDER_SAVE(46013, "communicationModelServiceProviderSaveError", true),
    ERR_CFPOINT_SAVE(46014, "communicationModelPointSaveError", true),
    ERR_CF_DELETE_DEPENDENCY(46015, "communicationModelDeleteDependencyError", true),
    ERR_COMMFLOW_STATUS_NOT_COMPLETE(46016, "commFlowStatusNotCompleteError", true),
    ERR_COMMFLOW_SERVICE_NOT_FOUND(46017, "commFlowServiceNotFoundError", true),
    ERR_CF_PRECHECK_NO_MACHINE(46018, "commFlowPrecheckFailedNoMachineBinding", true),
    ERR_CF_PRECHECK_NO_MF(46019, "commFlowPrecheckPailedNoMessageFlowBinding", true),

    /*
     * 47XXX: Activation related exceptions,mainly about PCo side
     */
    ERR_PCO_SERVER(47001, "pushPCoFailed", true),
    ERR_BPMN_INCONSISTENT(47002, "bpmnInconsistent", true),
    // ERR_PCO_TYPE_INCONSISTENT(47003, "pcoTypeInconsistent", true),
    ERR_PUSH_MULTICLINT_PROXY(47004, "pushMultiClientProxyFailed", true),
    ERR_PUSH_OPCUA_SINGLECLINET_PROXY(47005, "pushOPCUASingleClientProxyFailed", true),
    ERR_PUSH_NOTIFICATION(47006, "pushNotificationFailed", true),
    ERR_PUSH_SHOPFLOOR(47007, "pushShopFloorFailed", true),
    ERR_PARSE_BPMN(47008, "parseBpmnXmlFileFailed", true),
    ERR_OUTPUT_PARAM_NOT_ASSIGN(47009, "pushMulOutputNotAssigned", true),
    ERR_SERVICE_PARAM_NOT_ASSIGN(47010, "pushMulServiceParamNotAssigned", true),
    ERR_ASSIGNED_VAR_NOT_EXISTED(47011, "pushMulAssignedVariableNotExisted", true),
    ERR_VAR_PARAM_FOR_SRV_NOT_EXISTED(47012, "pushMulVarOrParamForSrvNotExisted", true),
    ERR_VAR_NOT_INITIATED(47013, "pushMulVarNotInitiated", true),
    ERR_VAR_ASSIGNED_SELF(47014, "pushMulVarAssignedBySelf", true),
    ERR_VAR_NOT_EXIST(47015, "pushMulVarNotExisted", true),
    ERR_VAR_PARAM_NOT_EXIST(47016, "pushMulVarOrParamNotExisted", true),
    ERR_VAR_PARAM_INVALID_TYPE(47017, "pushMulVarOrParamTypeInvalid", true),
    ERR_PREDEFINED_NOT_SUPPORT(47018, "pushMulPreDefinedNotSupport", true),
    ERR_PUSH_WEB_SERVICE_SINGLECLINET_PROXY(47019, "pushWebServiceSingleClientProxyFailed", true),

    /*
     * 48XXX: Locked
     */
    ERR_OBJECT_LOCKEDBYOTHER(48001, "objectLockedByOther", true),

    /*
     * 49XXX: Utils
     */
    ERR_TRANSFER_QVT(49001, "qvtTransformFailed", true), // XmlAnaylsisUtil
    ERR_PARSE_CONTENT(49002, "parseJsonFailed", true),

    // TODO : not used
    ERR_OBJECT_MUTABLE(40067, "objectTransientError", true),
    ERR_MD_SERVICE_METHOD_NOT_FOUND(40069, "serviceMethodNotFound", true),
    ERR_PARAM_MAPPING_NOT_EXIST(40112, "parameterMappingNotExist", true),;

    /* Error Code */
    private final int code;

    /* i18n handle key */
    private final String key;

    /* whether it need to be translated */
    private final boolean translate;

    ErrorCode(int code, String key, boolean transalate) {
        this.code = code;
        this.key = key;
        this.translate = transalate;
    }

    public String getKey() {
        return key;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "{\"code\":" + code + ",\"key\":" + key + ",\"translate\":" + translate + "}";
    }
}
