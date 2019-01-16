package com.technoride.abb.vendorapp.custom;

import com.technoride.abb.vendorapp.entity.ProductVariantDetail;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

public class CustomIntegerValuedColumnCellFroVariant extends TableCell<ProductVariantDetail,Integer>
{
    private TextField intValueTextField;

    @Override
    public void startEdit() {
        if (!isEmpty())
        {
            super.startEdit();
            createEntryBox();
            setText(null);
            setGraphic(intValueTextField);
            intValueTextField.selectAll();
            intValueTextField.requestFocus();
        }
    }


    @Override
    public void commitEdit(Integer newValue) {
        if (intValueTextField.getText().equalsIgnoreCase(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Parameter name required.");
            alert.setHeaderText("BLANK!!!");
            alert.setTitle("Required");
            alert.showAndWait();
            return;
        }
        else
        {
            int k = getTableView().getSelectionModel().getFocusedIndex();
            ProductVariantDetail item = getTableView().getItems().get(k);
            TablePosition position=getTableView().getSelectionModel().getSelectedCells().get(0);
            int col = position.getColumn();
            switch(col)
            {
                case 1:
                    item.setStartpos(newValue);
                    break;
                case 2:
                    item.setEndpos(newValue);
                    break;

            }
            setText(intValueTextField.getText());
            setGraphic(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        if (isEditing())
        {
            if (this.intValueTextField != null)
                this.intValueTextField.setText(null);
            setText(null);
            setGraphic(intValueTextField);
        }
        else
        {
            setText(String.valueOf(getItem()));
            setGraphic(null);
        }
    }


    private void createEntryBox()
    {
        intValueTextField =new TextField();
        intValueTextField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        intValueTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    commitEdit(Integer.parseInt(intValueTextField.getText()));
                    getTableView().getSelectionModel().selectNext();
                    int currentColIndex = getTableView().getColumns().indexOf(getTableColumn());
                    TableColumn nextCol = getTableView().getColumns().get(currentColIndex+1);
                    if (nextCol != null)
                        getTableView().edit(getTableRow().getIndex(), nextCol);
                }
            }
        });
    }
}
