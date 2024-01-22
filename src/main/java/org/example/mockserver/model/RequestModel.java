package org.example.mockserver.model;

import lombok.Data;

@Data
public class RequestModel {

    private Object request;
    private Object response;
    private String method;
    private Integer statusCode;
    private int count;


}
