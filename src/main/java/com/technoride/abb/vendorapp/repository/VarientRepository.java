package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.AnalysisLimits;
import com.technoride.abb.vendorapp.entity.ProductInfo;
import com.technoride.abb.vendorapp.entity.Varient;
import javafx.collections.ObservableList;

import java.util.List;

public interface VarientRepository {
    public List<Varient> getVarientFromId(long id);
    public boolean addProduct(Object [] parameters);
    public List<ProductInfo> getAllProductInfo();
    public boolean addAllParameters(ObservableList<AnalysisLimits> parameters);
}
