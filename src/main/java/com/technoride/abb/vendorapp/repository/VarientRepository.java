package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.*;
import javafx.collections.ObservableList;

import java.util.List;

public interface VarientRepository {
    public List<Varient> getVarientFromId(long id);
    public boolean addProduct(Object [] parameters);
    public List<ProductInfo> getAllProductInfo();
    public boolean addAllParameters(ObservableList<AnalysisLimits> parameters);
    public boolean addVariant(ProductVarient productVarient,Varient varient);
    public List<AnalysisLimits> getAllParameterFromProductCode(String productCode);
    public List<ProductVarient> getAllProductVarientFromProductCode(String productCode);
    public boolean addAllOverloadedVarient(List<AnalysisLimits> overload,long variantid);
}
