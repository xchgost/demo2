package com.example.demo2.common;

import com.example.demo2.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
;

@Getter
@ToString
@NoArgsConstructor
public class R<T> {
    /**
     * Interface response status code
     */
    private String code;

    /**
     * Interface response message
     */
    private String message;

    /**
     * Interface response data
     */
    private T data;

    public static <T> R<T> ok() {
        return new R<T>().code(StatusEnum.OK);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>().code(StatusEnum.OK).data(data);
    }

    public static <T> R<T> error() {
        return R.error(StatusEnum.SERVER_ERROR);
    }

    public static <T> R<T> error(StatusEnum statusEnum) {
        return R.error(statusEnum, null);
    }

    public static <T> R<T> error(StatusEnum statusEnum, String message) {
        return new R<T>()
                .code(statusEnum)
                .message(message == null ? statusEnum.getDefaultMessage() : message);
    }

    public R<T> data(T data) {
        this.data = data;
        return this;
    }

    public R<T> code(StatusEnum statusEnum) {
        return this.code(statusEnum.name());
    }

    public R<T> code(String code) {
        this.code = code;
        return this;
    }

    public R<T> message(String message) {
        this.message = message;
        return this;
    }
}
