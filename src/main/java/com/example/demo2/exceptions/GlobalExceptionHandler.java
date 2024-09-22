package com.example.demo2.exceptions;

import com.example.demo2.common.R;
import com.example.demo2.enums.StatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(VIException.class)
    public R<?> handleVIException(HttpServletRequest request, HttpServletResponse response, VIException e) {
        log.error("Code: {}, Message: {}", e.getCode(), e.getMessage(), e);
        return returnResponse(request, response, e.getCode(), e.getMessage(), e.getHttpStatus(), e.getData());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public R<?> handleConstraintViolationException(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException ex) {
        List<String> messages = new ArrayList<>();
        for (jakarta.validation.ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            messages.add(violation.getMessage());
        }
        return returnResponse(request, response, StatusEnum.INVALID_PARAMS.name(), messages.toString(), HttpStatus.BAD_REQUEST, null);
    }
    private R<?> returnResponse(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @Nullable String code,
                                @Nullable String message,
                                @Nullable HttpStatus httpStatus,
                                @Nullable Object data) {
        httpStatus = httpStatus != null ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR;
        HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(httpStatus.value());
        return returnResponse(request, response, code, message, httpStatusCode, data);
    }

    private R<?> returnResponse(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @Nullable String code,
                                @Nullable String message,
                                @Nullable HttpStatusCode httpStatusCode,
                                @Nullable Object data) {
        response.setStatus(httpStatusCode != null ? httpStatusCode.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());
        return R.ok(data)
                .code(code)
                .message(message);
    }
}