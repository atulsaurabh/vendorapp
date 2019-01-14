package com.technoride.abb.vendorapp.entity;

public class AnalysisLimits
{
    private String category;
    private String param_name;
    private double center;
    private double warningpct;
    private double errorpct;
    private String label;
    private short variable_limits;
    private double lcl;
    private double ucl;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public double getCenter() {
        return center;
    }

    public void setCenter(double center) {
        this.center = center;
    }

    public double getWarningpct() {
        return warningpct;
    }

    public void setWarningpct(double warningpct) {
        this.warningpct = warningpct;
    }

    public double getErrorpct() {
        return errorpct;
    }

    public void setErrorpct(double errorpct) {
        this.errorpct = errorpct;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public short getVariable_limits() {
        return variable_limits;
    }

    public void setVariable_limits(short variable_limits) {
        this.variable_limits = variable_limits;
    }

    public double getLcl() {
        return lcl;
    }

    public void setLcl(double lcl) {
        this.lcl = lcl;
    }

    public double getUcl() {
        return ucl;
    }

    public void setUcl(double ucl) {
        this.ucl = ucl;
    }
}
