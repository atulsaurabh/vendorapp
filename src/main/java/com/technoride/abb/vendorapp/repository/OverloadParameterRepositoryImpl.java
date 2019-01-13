package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.OverloadParameter;
import com.technoride.abb.vendorapp.entity.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class OverloadParameterRepositoryImpl implements OverloadParameterRepository
{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Parameter> getOverloadedParameter(long varientId)
    {
        String query = "SELECT * FROM overload_parameter where varientid=?";
        return jdbcTemplate.query(query, new Object[]{varientId}, new ResultSetExtractor<List<Parameter>>()
        {
            @Override
            public List<Parameter> extractData(ResultSet resultSet) throws SQLException, DataAccessException
            {
                List<Parameter> parameters = new ArrayList<>();
                while (resultSet.next())
                {
                    Parameter parameter=new Parameter();
                    parameter.setCenter(resultSet.getDouble("center"));
                    parameter.setParametername(resultSet.getString("parameter_name"));
                    parameter.setWarningrange(resultSet.getDouble("warning_pct"));
                    parameter.setErrorrange(resultSet.getDouble("error_pct"));
                    parameters.add(parameter);
                }
                return parameters;
            }
        }
        );
    }
}
