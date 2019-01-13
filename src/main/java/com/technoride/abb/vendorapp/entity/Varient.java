package com.technoride.abb.vendorapp.entity;

public class Varient
{
    private long id;
    private String barcode_part;
    private long product_var_id;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarcode_part() {
        return barcode_part;
    }

    public void setBarcode_part(String barcode_part) {
        this.barcode_part = barcode_part;
    }

    public long getProduct_var_id() {
        return product_var_id;
    }

    public void setProduct_var_id(long product_var_id) {
        this.product_var_id = product_var_id;
    }
}
