package com.technoride.abb.vendorapp.custom;

import com.technoride.abb.vendorapp.entity.AnalysisLimits;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public class CustomProductCodeColumnCell extends TableCell<AnalysisLimits,String>
{

    private TextField productCodeTextField;
    @Override
    public void startEdit() {
        if (!isEmpty())
        {
            super.startEdit();
            createEntryBox();
            setText("");
            setGraphic(productCodeTextField);
            productCodeTextField.selectAll();
        }

    }


    @Override
    protected void updateItem(String item, boolean empty) {
        //super.updateItem(item, empty);
        setGraphic(productCodeTextField);
    }


    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }


    private void createEntryBox()
    {
        productCodeTextField = new TextField();
    }
}
