package com.sepideh.notebook.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericRestResponse<T> {

    private T data;
    private String message;
    private String errorMessage;
    private int status;

    private int pageSize;
    private int pageNumber;
    private int totalPages;
    private long count;

    // Constructor *****************************************************************************************************
    public GenericRestResponse() { }

    public GenericRestResponse(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public GenericRestResponse(int status, String message, String errorMessage) {
        this.status = status;
        this.message = message;
        this.errorMessage = errorMessage;
    }

    public GenericRestResponse(T data, int status, int pageSize, int pageNumber, int totalPages, Long count) {
        this.data = data;
        this.status = status;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.count = count;
    }
}
