package com.paypal.bfs.test.employeeserv.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {

    private int code;
    private String message;
    private Employee employeeData;

    public ResponseObject(int code,
                          String message,
                          Employee employeeData) {
        this.code = code;
        this.message = message;
        this.employeeData = employeeData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Employee getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(Employee employeeData) {
        this.employeeData = employeeData;
    }
}
