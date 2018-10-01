package com.technoride.abb.vendorapp.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseConnectionCheckTask implements CustomTask
{
    @Autowired
    private DataSource dataSource;
    @Override
    public boolean finish()
    {
        try {
            Connection connection = dataSource.getConnection();

            if(!connection.isClosed())
                return true;
            else
                return false;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }



    }

    @Override
    public String message() {
        return "Database connection established successfully";
    }

    @Override
    public Enum<Priority> priority() {
        return Priority.HIGH;
    }
}
