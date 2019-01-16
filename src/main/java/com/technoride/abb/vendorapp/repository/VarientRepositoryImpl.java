package com.technoride.abb.vendorapp.repository;

import com.technoride.abb.vendorapp.entity.*;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.awt.event.ActionEvent;
import java.math.BigInteger;
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

    @Override
    public boolean addVariant(ProductVarient productVarient, Varient varient)
    {
        String query = "INSERT INTO product_varient VALUES(?,?,?,?,null)";
        Object [] params = new Object[]
                {
                        productVarient.getProductcode(),
                        productVarient.getVarientcode(),
                        productVarient.getStartchar(),
                        productVarient.getEndchar()
                };
        int result=jdbcTemplate.update(query,params,new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.INTEGER
        });

        if (result > 0)
        {
            query="SELECT AUTO_INCREMENT " +
                    "        FROM information_schema.TABLES " +
                    "        WHERE TABLE_NAME = ?" +
                    "        AND TABLE_SCHEMA = DATABASE()";
            SqlRowSet rowset=jdbcTemplate.queryForRowSet(query,new Object[]{"product_varient"});
            rowset.next();
            BigInteger id=rowset.getBigDecimal("AUTO_INCREMENT").toBigInteger();
            id=id.subtract(BigInteger.ONE);

            query = "INSERT INTO varient VALUES(null,?,?)";
            Object [] parameter=new Object[]
                    {
                            varient.getBarcode_part(),
                            id.intValue()
                    };
            int res=jdbcTemplate.update(query,parameter,new int[]{
                    Types.VARCHAR,
                    Types.INTEGER
            });

            if (res > 0)
                return true;
            else
                return false;
        }
        else
            return false;
    }


    @Override
    public List<AnalysisLimits> getAllParameterFromProductCode(String productCode)
    {
        String query = "SELECT * FROM analysislimits where productcode=?";
        return jdbcTemplate.query(query, new Object[]{productCode}, new ResultSetExtractor<List<AnalysisLimits>>() {
            @Override
            public List<AnalysisLimits> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<AnalysisLimits> parameters = new ArrayList<>();
                while (resultSet.next())
                {
                    AnalysisLimits parameter=new AnalysisLimits();
                    parameter.setParam_name(resultSet.getString("param_name"));
                    parameter.setCenter(resultSet.getDouble("center"));
                    parameter.setWarningpct(resultSet.getDouble("warning_pct"));
                    parameter.setErrorpct(resultSet.getDouble("error_pct"));
                    parameter.setLabel(resultSet.getString("label"));
                    parameter.setVariable_limits(resultSet.getShort("variable_limits"));
                    parameter.setLcl(resultSet.getDouble("lcl"));
                    parameter.setUcl(resultSet.getDouble("ucl"));
                    parameters.add(parameter);
                }
                return parameters;
            }
        });
    }


    @Override
    public List<ProductVarient> getAllProductVarientFromProductCode(String productCode)
    {

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
                    productVarient.setEndchar(resultSet.getInt("endchar"));
                    productVarient.setStartchar(resultSet.getInt("startchar"));
                    productVarient.setVarientcode(resultSet.getString("varient_code"));
                    productVarients.add(productVarient);
                }
                return productVarients;
            }
        });
    }


    @Override
    public boolean addAllOverloadedVarient(List<AnalysisLimits> overload,long variantid) {
        String query = "INSERT INTO overload_parameter VALUES("+variantid+",?,?,?,?,null)";
        List<Object []> params = overload.stream().map(this::getObjectArrayCustom).collect(Collectors.toList());
        int [] res=jdbcTemplate.batchUpdate(query,params,new int[]{
                Types.VARCHAR,
                Types.DOUBLE,
                Types.DOUBLE,
                Types.DOUBLE
        });
        return Arrays.stream(res).allMatch(value -> value > 0);
    }


    private Object [] getObjectArrayCustom(AnalysisLimits analysisLimits)
    {
        return new Object[]
                {
                  analysisLimits.getParam_name(),
                        analysisLimits.getCenter(),
                        analysisLimits.getWarningpct(),
                        analysisLimits.getErrorpct()
                };
    }
}
