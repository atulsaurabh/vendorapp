package com.technoride.abb.vendorapp.entity;

public class ProductVariantDetail
{
    private String variantcode;
    private int startpos;
    private int endpos;
    private String barcode;


    public String getVariantcode() {
        return variantcode;
    }

    public void setVariantcode(String variantcode) {
        this.variantcode = variantcode;
    }

    public int getStartpos() {
        return startpos;
    }

    public void setStartpos(int startpos) {
        this.startpos = startpos;
    }

    public int getEndpos() {
        return endpos;
    }

    public void setEndpos(int endpos) {
        this.endpos = endpos;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
