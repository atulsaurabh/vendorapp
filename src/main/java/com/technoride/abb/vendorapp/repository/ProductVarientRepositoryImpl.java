package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.ProductVarient;
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
public class ProductVarientRepositoryImpl implements ProductVarientRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProductVarient> getProductVarient(String productCode) {

        String query = "SELECT * FROM product_varient where product_code=?";
        return jdbcTemplate.query(query, new Object[]{productCode}, new ResultSetExtractor<List<ProductVarient>>() {
            @Override
            public List<ProductVarient> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

                List<ProductVarient> productVarients = new ArrayList<>();
                while (resultSet.next())
                {
                    ProductVarient productVarient = new ProductVarient();
                    productVarient.setId(resultSet.getLong("id"));
                    productVarient.setProductcode(resultSet.getString("product_code"));
                    productVarient.setVarientcode(resultSet.getString("varient_code"));
                    productVarient.setStartchar(resultSet.getInt("startchar"));
                    productVarient.setEndchar(resultSet.getInt("endchar"));
                    productVarients.add(productVarient);
                }
                return productVarients;
            }
        });
    }
}
