package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.ProductVarient;

import java.util.List;

public interface ProductVarientRepository
{
    public List<ProductVarient> getProductVarient(String productCode);
}
