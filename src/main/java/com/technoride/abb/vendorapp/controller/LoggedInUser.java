package com.technoride.abb.vendorapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoggedInUser {

    public static StringProperty userProperty=new SimpleStringProperty();

    private LoggedInUser()
    {

    }

    public static final void setUser(String user)
    {
        userProperty.set(user);
    }

    public static final String getUser()
    {
        return userProperty.get();
    }

}
