package com.example.demo2.enums;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusEnum {
    INVALID_PARAMS("Invalid params", HttpStatus.BAD_REQUEST),
    REQUEST_FAILED("The operation failed", HttpStatus.BAD_REQUEST),
    RESPONSE_FAILED("The operation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ADDRESS_ALREADY_REGISTERED("Address already registered", HttpStatus.FORBIDDEN),
    SERVER_ERROR("Server error", HttpStatus.INTERNAL_SERVER_ERROR),
    OK("OK", HttpStatus.OK);

    private final String defaultMessage;
    private final HttpStatus httpStatus;

    StatusEnum(@NonNull String defaultMessage) {
        this(defaultMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    StatusEnum(@NonNull String defaultMessage, @NonNull HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }
}
