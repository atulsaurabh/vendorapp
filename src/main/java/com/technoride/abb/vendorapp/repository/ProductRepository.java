package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.Parameter;
import com.technoride.abb.vendorapp.entity.ProductInfo;

import java.util.List;

public interface ProductRepository
{
    public ProductInfo getProductInfo(String barcode);
    public List<Parameter> getParamers(String productcode);
}
