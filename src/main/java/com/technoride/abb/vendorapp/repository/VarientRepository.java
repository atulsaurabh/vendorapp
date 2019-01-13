package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.Varient;

import java.util.List;

public interface VarientRepository {
    public List<Varient> getVarientFromId(long id);
}
