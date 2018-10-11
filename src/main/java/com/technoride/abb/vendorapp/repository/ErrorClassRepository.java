package com.technoride.abb.vendorapp.repository;


import com.technoride.abb.vendorapp.entity.ErrorClass;
import com.technoride.abb.vendorapp.entity.TestRecord;
import com.technoride.abb.vendorapp.entity.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ErrorClassRepository  implements TestRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ErrorClass> errorClass()
    {
        String query="SELECT * FROM errorclass";
        List<ErrorClass> errorClasses = jdbcTemplate.query(query, new RowMapper<ErrorClass>() {
            @Override
            public ErrorClass mapRow(ResultSet resultSet, int i) throws SQLException {
                ErrorClass errorClass = new ErrorClass();
                errorClass.setErrorcode(resultSet.getShort("id"));
                errorClass.setErrorclassname(resultSet.getString("errclassname"));
                return errorClass;
            }
        });
        return errorClasses;
    }

    @Override
    @Transactional
    public boolean insertTestRecoder(TestRecord testRecord, List<TestResult> testResults)
    {
        String query = "insert into testrecord values(null,?,?,?,?,?,?,?,?,?,?,?)";
        Object args[] = new Object[]
                {
                        testRecord.getCategory(),
                        testRecord.getTestStation(),
                        testRecord.getDut(),
                        testRecord.getOrdercode(),
                        testRecord.getStarttime(),
                        testRecord.getEndtime(),
                        testRecord.getResult(),
                        testRecord.getResultcode(),
                        testRecord.getUserdesc(),
                        testRecord.getIncludeinreport(),
                        testRecord.getErrorcategory()
                };
        int rows=jdbcTemplate.update(query,args);
        if (rows > 0)
        {
           query="SELECT AUTO_INCREMENT " +
                   "        FROM information_schema.TABLES " +
                   "        WHERE TABLE_NAME = ?" +
                   "        AND TABLE_SCHEMA = DATABASE()";
           SqlRowSet rowset=jdbcTemplate.queryForRowSet(query,new Object[]{"testrecord"});
           rowset.next();
            BigInteger id=rowset.getBigDecimal("AUTO_INCREMENT").toBigInteger();
            /*testResults.forEach(testResult -> {
                testResult.setTest_record_id(id);
            });*/

            query="INSERT INTO testresult values(null,?,?,?,?)";
            for (TestResult tr : testResults)
            {
              Object [] arg=new Object[]
                      {
                         id.subtract(BigInteger.ONE),
                         tr.getParam_name(),
                         tr.getParamvalue(),
                         tr.getResult()
                      };

              jdbcTemplate.update(query,arg);
            }
            return true;
        }
        else
        return false;
    }
}
