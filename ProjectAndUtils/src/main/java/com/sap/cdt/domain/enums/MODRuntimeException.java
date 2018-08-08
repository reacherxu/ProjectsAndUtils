package com.sap.cdt.domain.enums;

import java.util.ArrayList;
import java.util.List;

public class MODRuntimeException extends RuntimeException {

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 8496088933327436769L;

    private ErrorCode         errorCode        = ErrorCode.ERROR;
    private ErrorType         errorType        = ErrorType.MESSAGE;
    private List<Object>      params           = new ArrayList<>();

    public MODRuntimeException(Exception e) {
        super(e);
    }

    public MODRuntimeException(Exception e, ErrorCode errorCode) {
        super(e);
        this.errorCode = errorCode;
    }

    public MODRuntimeException() {
        super();
    }

    public MODRuntimeException(ErrorCode errorCode, Object... args) {
        super();
        this.errorCode = errorCode;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public MODRuntimeException(ErrorCode errorCode, ErrorType errorType, Object... args) {
        super();
        this.errorCode = errorCode;
        this.errorType = errorType;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public MODRuntimeException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MODRuntimeException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace, ErrorCode errorCode, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public MODRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Machine orchestration designer runtime exception
     * Contains all known and unknown exceptions
     * 
     * @param message  detailed message shown in dialog
     * @param cause    exception thrown
     * @param errorCode  error code 
     * @param args    place holder for other arguments 
     */
    public MODRuntimeException(String message, Throwable cause, ErrorCode errorCode,
                               Object... args) {
        super(message, cause);
        this.errorCode = errorCode;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public MODRuntimeException(String message, Throwable cause, ErrorCode errorCode,
                               ErrorType errorType, Object... args) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorType = errorType;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public MODRuntimeException(String message) {
        super(message);
    }

    public MODRuntimeException(String message, ErrorCode errorCode, Object... args) {
        super(message);
        this.errorCode = errorCode;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public MODRuntimeException(String message, ErrorCode errorCode, ErrorType errorType,
                               Object... args) {
        super(message);
        this.errorCode = errorCode;
        this.errorType = errorType;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public MODRuntimeException(Throwable cause) {
        super(cause);
    }

    public MODRuntimeException(Throwable cause, ErrorCode errorCode, Object... args) {
        super(cause);
        this.errorCode = errorCode;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public MODRuntimeException(Throwable cause, ErrorCode errorCode, ErrorType errorType,
                               Object... args) {
        super(cause);
        this.errorCode = errorCode;
        this.errorType = errorType;
        for (Object arg : args) {
            params.add(arg);
        }
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
