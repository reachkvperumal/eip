package com.kv.dsl.eip.dto;

import java.io.Serializable;


public class ResponseDO implements Serializable {
    private Integer code;
    private String data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
