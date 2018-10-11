package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.Parameter;
import com.technoride.abb.vendorapp.entity.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ProductInfo getProductInfo(String barcode)
    {
        String query = "SELECT * FROM product WHERE ordercode=?";
        return  jdbcTemplate.query(query, new Object[]{barcode}, new ResultSetExtractor<ProductInfo>() {
            @Override
            public ProductInfo extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                try {
                    if(resultSet.next()) {
                        ProductInfo productInfo = new ProductInfo();
                        productInfo.setBarcode(barcode);
                        productInfo.setProductname(resultSet.getString("productname"));
                        productInfo.setProductcode(resultSet.getString("productcode"));
                        return productInfo;
                    }
                    else
                        return null;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                return null;

            }
        });
    }

    @Override
    public List<Parameter> getParamers(String productcode)
    {
        String query = "SELECT * FROM analysislimits WHERE productcode=?";
        List<Parameter> parameters=jdbcTemplate.query(query, new Object[]{productcode}, new RowMapper<Parameter>() {
            @Override
            public Parameter mapRow(ResultSet resultSet, int i) throws SQLException {
                Parameter parameter=new Parameter();
                parameter.setParametername(resultSet.getString("param_name"));
                parameter.setCenter(resultSet.getDouble("center"));
                parameter.setLabel(resultSet.getString("label"));
                parameter.setWarningrange(resultSet.getDouble("warning_pct"));
                parameter.setErrorrange(resultSet.getDouble("error_pct"));
                parameter.setUcl(resultSet.getDouble("ucl"));
                parameter.setLcl(resultSet.getDouble("lcl"));
                parameter.setVariable_limits(resultSet.getShort("variable_limits"));

                return parameter;
            }
        });
        return parameters;
    }
}
