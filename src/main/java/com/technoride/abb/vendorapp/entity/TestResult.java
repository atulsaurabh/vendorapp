package com.technoride.abb.vendorapp.entity;

import java.math.BigInteger;

public class TestResult {
    private BigInteger id;
    private BigInteger test_record_id;
    private String param_name;
    private double paramvalue;
    private short result;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getTest_record_id() {
        return test_record_id;
    }

    public void setTest_record_id(BigInteger test_record_id) {
        this.test_record_id = test_record_id;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public double getParamvalue() {
        return paramvalue;
    }

    public void setParamvalue(double paramvalue) {
        this.paramvalue = paramvalue;
    }

    public short getResult() {
        return result;
    }

    public void setResult(short result) {
        this.result = result;
    }
}
