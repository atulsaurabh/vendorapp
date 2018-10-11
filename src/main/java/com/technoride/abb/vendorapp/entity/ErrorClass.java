package com.technoride.abb.vendorapp.entity;

public class ErrorClass
{
    private String errorclassname;
    private short errorcode;

    public String getErrorclassname() {
        return errorclassname;
    }

    public void setErrorclassname(String errorclassname) {
        this.errorclassname = errorclassname;
    }

    public short getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(short errorcode) {
        this.errorcode = errorcode;
    }

    @Override
    public String toString() {
        return errorclassname;
    }
}
