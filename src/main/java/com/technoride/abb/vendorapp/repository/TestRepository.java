package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.ErrorClass;
import com.technoride.abb.vendorapp.entity.TestRecord;
import com.technoride.abb.vendorapp.entity.TestResult;

import java.util.List;

public interface TestRepository {
    public List<ErrorClass> errorClass();
    public boolean insertTestRecoder(TestRecord testRecord, List<TestResult> testResults);
}
