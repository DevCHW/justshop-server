package com.justshop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"code", "status", "message", "data"})
public class ApiResponse<T> {

    private String code;
    private HttpStatus status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.code = String.valueOf(status.value());
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data, HttpStatus httpStatus, String message) {
        return new ApiResponse<>(httpStatus, message, data);
    }

    public static <T> ApiResponse<T> of(T data, HttpStatus httpStatus) {
        return ApiResponse.of(data, httpStatus, httpStatus.name());
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.of(data, HttpStatus.OK, HttpStatus.OK.name());
    }

    public static ApiResponse ok() {
        return ApiResponse.of(null, HttpStatus.OK, HttpStatus.OK.name());
    }

    public static <T> ApiResponse<T> created() {
        return ApiResponse.of(null, HttpStatus.CREATED, HttpStatus.CREATED.name());
    }

}
