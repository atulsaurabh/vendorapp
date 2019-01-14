package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.AnalysisLimits;
import com.technoride.abb.vendorapp.entity.ProductInfo;
import com.technoride.abb.vendorapp.entity.Varient;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VarientRepositoryImpl implements VarientRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Varient> getVarientFromId(long id)
    {
        String query = "SELECT * FROM varient where product_var_id=?";
        return jdbcTemplate.query(query, new Object[]{id}, new ResultSetExtractor<List<Varient>>() {
            @Override
            public List<Varient> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Varient> varients = new ArrayList<>();
                while (resultSet.next())
                {
                    Varient varient=new Varient();
                    varient.setProduct_var_id(resultSet.getLong("product_var_id"));
                    varient.setBarcode_part(resultSet.getString("barcode_part"));
                    varient.setId(resultSet.getLong("varient_id"));
                    varients.add(varient);
                }
                return varients;
            }
        });
    }

    @Override
    public boolean addProduct(Object[] parameters)
    {

        String query = "INSERT INTO product VALUES(?,?,?,?)";
        return (jdbcTemplate.update(query,
                parameters,
                new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                        Types.VARCHAR
        }) > 0);

    }


    @Override
    public List<ProductInfo> getAllProductInfo() {
        String query = "SELECT * FROM product";
        return jdbcTemplate.query(query, new Object[]{}, new ResultSetExtractor<List<ProductInfo>>() {
            @Override
            public List<ProductInfo> extractData(ResultSet resultSet) throws SQLException, DataAccessException
            {
                List<ProductInfo> productInfos = new ArrayList<>();
                while (resultSet.next())
                {
                    ProductInfo info = new ProductInfo();
                    info.setBarcode(resultSet.getString("ordercode"));
                    info.setProductcode(resultSet.getString("productcode"));
                    info.setProductname(resultSet.getString("productname"));
                    info.setProductcategorycode(resultSet.getString("product_cat_code"));
                    productInfos.add(info);
                }
                return productInfos;
            }
        });
    }


    @Override
    public boolean addAllParameters(ObservableList<AnalysisLimits> parameters) {
        List<Object[]> objects = parameters.stream().map(this::getObjectArray).collect(Collectors.toList());
        String batchQuery = "INSERT INTO analysislimits values(null,?,?,?,?,?,?,?,?,?,?)";

        int [] results=jdbcTemplate.batchUpdate(batchQuery,objects,new int[]{
            Types.VARCHAR,
            Types.VARCHAR,
            Types.DOUBLE,
            Types.DOUBLE,
            Types.DOUBLE,
            Types.VARCHAR,
            Types.TINYINT,
            Types.DOUBLE,
            Types.DOUBLE
        });
        return Arrays.stream(results).allMatch(value ->
            value > 0
        );
    }

    private Object [] getObjectArray(AnalysisLimits analysisLimits)
    {
        return new Object[]
                {
                        analysisLimits.getCategory(),
                        analysisLimits.getParam_name(),
                        analysisLimits.getCenter(),
                        analysisLimits.getWarningpct(),
                        analysisLimits.getErrorpct(),
                        analysisLimits.getLabel(),
                        analysisLimits.getVariable_limits(),
                        analysisLimits.getLcl(),
                        analysisLimits.getUcl(),
                        analysisLimits.getCategory()
                };
    }
}
