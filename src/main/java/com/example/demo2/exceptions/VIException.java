package com.example.demo2.exceptions;

import com.example.demo2.enums.StatusEnum;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.io.Serial;

@Getter
public class VIException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private final Object data;

    public VIException(@Nullable String message) {
        this(StatusEnum.SERVER_ERROR, message, null);
    }

    public VIException(@NonNull String code, @Nullable String message) {
        this(code, message, null);
    }

    public VIException(@NonNull String code, @Nullable String message, @Nullable HttpStatus httpStatus) {
        this(code, message, httpStatus, null);
    }

    public VIException(@NonNull String code, @Nullable String message, @Nullable HttpStatus httpStatus, @Nullable Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus;
        this.data = data;
    }

    public VIException(@NonNull StatusEnum statusEnum) {
        this(statusEnum, statusEnum.getDefaultMessage(), statusEnum.getHttpStatus());
    }

    public VIException(@NonNull StatusEnum statusEnum, @Nullable String message) {
        this(statusEnum, message, statusEnum.getHttpStatus(), null);
    }

    public VIException(@NonNull StatusEnum statusEnum, @Nullable String message, @Nullable HttpStatus httpStatus) {
        this(statusEnum, message, httpStatus, null);
    }

    public VIException(@NonNull StatusEnum statusEnum, @Nullable String message, @Nullable HttpStatus httpStatus, @Nullable Object data) {
        super(message);
        this.code = statusEnum.name();
        this.message = message == null
                ? statusEnum.getDefaultMessage()
                : message;
        this.httpStatus = httpStatus == null
                ? statusEnum.getHttpStatus()
                : httpStatus;
        this.data = data;
    }
}
