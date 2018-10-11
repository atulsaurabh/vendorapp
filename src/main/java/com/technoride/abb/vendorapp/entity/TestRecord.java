package com.technoride.abb.vendorapp.entity;

import java.math.BigInteger;

public class TestRecord {
    private BigInteger id;
    private String category;
    private String testStation;
    private String dut;
    private String ordercode;
    private String starttime;
    private String endtime;
    private short result;
    private short resultcode;
    private String userdesc;
    private short includeinreport;
    private short errorcategory;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTestStation() {
        return testStation;
    }

    public void setTestStation(String testStation) {
        this.testStation = testStation;
    }

    public String getDut() {
        return dut;
    }

    public void setDut(String dut) {
        this.dut = dut;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public short getResult() {
        return result;
    }

    public void setResult(short result) {
        this.result = result;
    }

    public short getResultcode() {
        return resultcode;
    }

    public void setResultcode(short resultcode) {
        this.resultcode = resultcode;
    }

    public String getUserdesc() {
        return userdesc;
    }

    public void setUserdesc(String userdesc) {
        this.userdesc = userdesc;
    }

    public short getIncludeinreport() {
        return includeinreport;
    }

    public void setIncludeinreport(short includeinreport) {
        this.includeinreport = includeinreport;
    }

    public short getErrorcategory() {
        return errorcategory;
    }

    public void setErrorcategory(short errorcategory) {
        this.errorcategory = errorcategory;
    }
}
