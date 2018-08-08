package com.sap.projectAndUtils;

/**
 * SAP Inc.
 * Copyright (c) 1972-2018 All Rights Reserved.
 */

import com.sap.cdt.domain.enums.ErrorCode;
import com.sap.cdt.domain.enums.ErrorType;
import com.sap.cdt.domain.enums.MODRuntimeException;

/**
 * The purpose is to reduce the useless branches 
 * and improve the code coverage as well as the code style
 * 
 * @author richard.xu03@sap.com
 * @version $Id: AssertUtil.java, v 0.1 Jun 6, 2018 10:59:14 AM richard.xu Exp $
 */
public class AssertUtil {

    /**
     * judge if the expression is true
     * true - do nothing
     * false - throw exception
     * 
     * @param exp   expression
     * @param errorCode
     * @param errorType
     * @param errorMsgs
     */
    public static void assertTrue(Boolean exp, ErrorCode errorCode, ErrorType errorType,
                                  Object... errorMsgs) {
        if (!exp) {
            throw new MODRuntimeException(errorCode, errorType, errorMsgs);
        }
    }

    public static void assertTrue(Boolean exp, String detailMsg, ErrorCode errorCode,
                                  ErrorType errorType, Object... errorMsgs) {
        if (!exp) {
            throw new MODRuntimeException(detailMsg, errorCode, errorType, errorMsgs);
        }
    }

    public static void assertTrue(Boolean exp, ErrorCode errorCode) {
        assertTrue(exp, errorCode, ErrorType.MESSAGE);
    }

    /**
     * must not be null,if null then throw exception
     * 
     * @param obj
     * @param errorCode
     * @param errorType
     * @param errorMsgs
     */
    public static void assertNotNull(Object obj, ErrorCode errorCode, ErrorType errorType,
                                     Object... errorMsgs) {
        if (null == obj) {
            throw new MODRuntimeException(errorCode, errorType, errorMsgs);
        }
    }

    public static void assertNotNull(Object obj, ErrorCode errorCode) {
        assertNotNull(obj, errorCode, ErrorType.MESSAGE);
    }

    /**
     * must be null, if not null,then throw exception
     * 
     * @param obj
     * @param errorCode
     * @param errorType
     * @param errorMsgs
     */
    public static void assertNull(Object obj, ErrorCode errorCode, ErrorType errorType,
                                  Object... errorMsgs) {
        if (null != obj) {
            throw new MODRuntimeException(errorCode, errorType, errorMsgs);
        }
    }

    public static void assertNull(Object obj, ErrorCode errorCode) {
        assertNull(obj, errorCode, ErrorType.MESSAGE);
    }

    public static void assertFalse(Boolean exp, ErrorCode errorCode, ErrorType errorType,
                                   Object... errorMsgs) {
        assertTrue(!exp, errorCode, errorType, errorMsgs);
    }

    public static void assertFalse(Boolean exp, ErrorCode errorCode) {
        assertFalse(exp, errorCode, ErrorType.MESSAGE);
    }

}
