package com.technoride.abb.vendorapp.entity;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;


public class Parameter
{
    private String testtype;
    private TextField testValue=new TextField();
    private StringProperty parametername = new SimpleStringProperty();
    private DoubleProperty center = new SimpleDoubleProperty();
    private DoubleProperty warningrange = new SimpleDoubleProperty();
    private DoubleProperty errorrange=new SimpleDoubleProperty();
    private StringProperty label=new SimpleStringProperty();
    private IntegerProperty variable_limits=new SimpleIntegerProperty();
    private DoubleProperty lcl=new SimpleDoubleProperty();
    private DoubleProperty ucl=new SimpleDoubleProperty();
    private NumberBinding minvalue=Bindings.subtract(center,center.multiply(errorrange).divide(100));
    private NumberBinding maxvalue = Bindings.add(center,center.multiply(errorrange).divide(100));
    private StringProperty testvalue=new SimpleStringProperty();
    private boolean fail;

    public boolean isFail() {
        return fail;
    }


    public String getParametername()
   {
       return parametername.get();
   }

   public void setParametername(String parametername)
   {
       this.parametername.set(parametername);
   }

    public double getCenter() {
        return center.get();
    }

    public void setCenter(double center) {
        this.center.set(center);
    }

    public double getWarningrange() {
        return warningrange.get();
    }

    public DoubleProperty warningrangeProperty() {
        return warningrange;
    }

    public void setWarningrange(double warningrange) {
        this.warningrange.set(warningrange);
    }

    public double getErrorrange() {
        return errorrange.get();
    }

    public DoubleProperty errorrangeProperty() {
        return errorrange;
    }

    public void setErrorrange(double errorrange) {
        this.errorrange.set(errorrange);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public int getVariable_limits() {
        return variable_limits.get();
    }

    public IntegerProperty variable_limitsProperty() {
        return variable_limits;
    }

    public void setVariable_limits(int variable_limits) {
        this.variable_limits.set(variable_limits);
    }

    public double getLcl() {
        return lcl.get();
    }

    public DoubleProperty lclProperty() {
        return lcl;
    }

    public void setLcl(double lcl) {
        this.lcl.set(lcl);
    }

    public double getUcl() {
        return ucl.get();
    }

    public DoubleProperty uclProperty() {
        return ucl;
    }

    public void setUcl(double ucl) {
        this.ucl.set(ucl);
    }

    public double getMinvalue()
    {
        return minvalue.doubleValue();
    }

    public double getMaxvalue()
    {
        return maxvalue.doubleValue();
    }

    private BooleanProperty booleanProperty = new SimpleBooleanProperty();

    public double getTextTestResult()
    {
        return Double.parseDouble(testvalue.get());
    }

    public boolean getBooleanTestResult()
    {
        return booleanProperty.getValue();
    }


    public String getTesttype()
    {
        return testtype;
    }


    public Control getTestValue() {
       if(errorrange.get() == 0.00 || errorrange.get() == 100.00)
       {
           testtype="BOOLEAN";
           CheckBox checkBox = new CheckBox();
           checkBox.selectedProperty().bindBidirectional(booleanProperty);
           checkBox.setSelected(true);
           checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
               @Override
               public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                   if(newValue == false)
                       fail=true;
                   else
                       fail=false;
               }
           });
           return checkBox;
       }

       else {
           testtype="TEXT";
           testValue.textProperty().bindBidirectional(testvalue);
           testValue.textProperty().addListener(new ChangeListener<String>() {
               @Override
               public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   if (!newValue.trim().equals(""))
                   {
                       double value = Double.parseDouble(testValue.getText().trim());
                       if(value < minvalue.doubleValue() || value > maxvalue.doubleValue())
                           //testValue.setStyle("-fx-border-color: red");
                           fail=true;
                       else
                           fail=false;
                   }
               }
           });

           return testValue;
       }
    }


}
