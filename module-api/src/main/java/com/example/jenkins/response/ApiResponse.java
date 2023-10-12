package com.example.jenkins.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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

    // Factory method
    public static <T> ApiResponse<T> of(T data, HttpStatus httpStatus, String message) {
        return new ApiResponse<>(httpStatus, message, data);
    }

    // 200 OK
    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.of(data, HttpStatus.OK, HttpStatus.OK.name());
    }

    // 200 OK
    public static ApiResponse ok() {
        return ApiResponse.of(null, HttpStatus.OK, HttpStatus.OK.name());
    }

    // 201 CREATED
    public static <T> ApiResponse<T> created() {
        return ApiResponse.of(null, HttpStatus.CREATED, HttpStatus.CREATED.name());
    }

    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.of(data, HttpStatus.CREATED, HttpStatus.CREATED.name());
    }

    // 204 NO CONTENT
    public static <T> ApiResponse<T> noContent() {
        return ApiResponse.of(null, HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.name());
    }

}
