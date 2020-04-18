package it.italiancoders.pizzashop.gateway.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    ADMIN("ADMIN"),
    WORKER("WORKER"),
    CUSTOMER("CUSTOMER");

    private String value;

    @JsonValue
    public String getValue() {
        return value;
    }
    private UserRole(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static UserRole fromValue(String value) {
        for (UserRole b : UserRole.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

