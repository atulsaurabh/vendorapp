package com.technoride.abb.vendorapp.configuration;


import com.technoride.abb.vendorapp.loader.VendorAppLoader;
import com.technoride.abb.vendorapp.task.DatabaseConnectionCheckTask;
import com.technoride.abb.vendorapp.task.InitialTask;
import com.technoride.abb.vendorapp.util.Util;
import com.technoride.abb.vendorapp.util.VendorAppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@ComponentScan(basePackages = {
                               "com.technoride.abb.vendorapp.loader",
                                "com.technoride.abb.vendorapp.controller",
                                "com.technoride.abb.vendorapp.task",
                                "com.technoride.abb.vendorapp.repository"
})
@PropertySources(value = {
        @PropertySource(value = "classpath:applicationContext.properties")
}

)
public class BaseConfiguration
{

  @Autowired
  private Environment environment;



  @Bean(name = "vendorAppLoader")
    public VendorAppLoader vendorAppLoader()
  {
      return new VendorAppLoader();
  }

  @Bean
  public DataSource dataSource()
  {
    /*String url = environment.getProperty("db.url");
    String username = environment.getProperty("db.username");
    String password = environment.getProperty("db.password");
    String driverClassName=environment.getProperty("db.driverClassName");*/
    DriverManagerDataSource dataSource=new DriverManagerDataSource();
    HashMap<String,String> dbMap = DbConfig.dbparams;
    System.out.println(dbMap.get("db.driverClassName"));
    dataSource.setDriverClassName(dbMap.get("db.driverClassName"));
    dataSource.setPassword(dbMap.get("db.password"));
    dataSource.setUrl(dbMap.get("db.url"));
    dataSource.setUsername(dbMap.get("db.username"));
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate()
  {
     JdbcTemplate template=new JdbcTemplate(dataSource());
     return template;
  }

  @Bean
  public InitialTask initialTask()
  {
    return new InitialTask();
  }

  @Bean
  public DatabaseConnectionCheckTask databaseConnectionCheckTask()
  {
    return new DatabaseConnectionCheckTask();
  }

  @Bean
  public Util util()
  {
    return new VendorAppUtil();
  }
}
