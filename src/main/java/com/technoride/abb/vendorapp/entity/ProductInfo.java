package com.technoride.abb.vendorapp.entity;

public class ProductInfo {
    private String barcode;
    private String productname;
    private String productcode;
    private String serialnumber;
    private String productcategorycode;


    public String getProductcategorycode() {
        return productcategorycode;
    }

    public void setProductcategorycode(String productcategorycode) {
        this.productcategorycode = productcategorycode;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    @Override
    public String toString() {
        return productname+"("+productcode+")";
    }
}
