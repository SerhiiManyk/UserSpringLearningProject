package com.manser.pr.domain;

public enum SortField {
    EMAIL("email"),
    NAME("name"),
    PHONE_NUMBER("phone"),
    ROLE("userRole");

    private final String dbField;

    SortField(String dbField) {
        this.dbField = dbField;
    }

    public String getDbField() {
        return dbField;
    }
}
