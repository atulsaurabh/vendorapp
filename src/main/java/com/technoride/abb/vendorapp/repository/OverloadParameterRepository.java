package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.Parameter;

import java.util.List;

public interface OverloadParameterRepository {

    public List<Parameter> getOverloadedParameter(long varientId);
}
