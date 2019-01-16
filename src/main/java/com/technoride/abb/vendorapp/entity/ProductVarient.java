package com.technoride.abb.vendorapp.entity;

public class ProductVarient {
    private long id;
    private int startchar;
    private int endchar;
    private String productcode;
    private String varientcode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStartchar() {
        return startchar;
    }

    public void setStartchar(int startchar) {
        this.startchar = startchar;
    }

    public int getEndchar() {
        return endchar;
    }

    public void setEndchar(int endchar) {
        this.endchar = endchar;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getVarientcode() {
        return varientcode;
    }

    public void setVarientcode(String varientcode) {
        this.varientcode = varientcode;
    }


    @Override
    public String toString() {
        return varientcode;
    }
}
