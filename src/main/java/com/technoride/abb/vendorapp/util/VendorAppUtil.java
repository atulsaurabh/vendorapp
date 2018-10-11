package com.technoride.abb.vendorapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class VendorAppUtil implements Util
{
    @Autowired
    private Environment environment;

    @Override
    public String findTestStationName() {
        return environment.getProperty("teststartion.name");
    }

    @Override
    public int findBarcodeLength()
    {
        return Integer.parseInt(environment.getProperty("barcode.length"));
    }
}
