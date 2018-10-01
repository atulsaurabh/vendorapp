package com.technoride.abb.vendorapp.task;

public interface CustomTask {

    public boolean finish();
    public String message();
    public Enum<Priority> priority();
}
