package com.sap.cdt.domain.enums;

public enum ErrorType {
    MESSAGE(1, "message"),
    EXCEPTION(2, "exception"),;

    private int key;
    private String type;

    ErrorType(int key, String type) {
        this.key = key;
        this.type = type;
    }

    public int getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{\"key\":" + key + ",\"type\":" + type + "}";
    }
}
