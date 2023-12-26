package com.example.ecommerce.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseBuilder {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    private HttpStatus status;

    public ResponseBuilder() {
    }

    public ResponseBuilder(Object data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    public ResponseBuilder(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
