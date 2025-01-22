package spring.security.master.exception.handler;

import lombok.Getter;

@Getter
public enum StatusEnums {
    OK("OK"),
    FAIL("FAIL");

    private final String status;

    StatusEnums(String status) {
        this.status = status;
    }
}
