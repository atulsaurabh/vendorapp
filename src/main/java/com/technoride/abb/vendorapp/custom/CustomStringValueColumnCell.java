package com.technoride.abb.vendorapp.custom;

import com.technoride.abb.vendorapp.entity.AnalysisLimits;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

public class CustomStringValueColumnCell extends TableCell<AnalysisLimits,String>
{
    private TextField stringValueTextField;

    @Override
    public void startEdit() {
        if (!isEmpty())
        {
            super.startEdit();
            createEntryBox();
            setText(null);
            setGraphic(stringValueTextField);
            stringValueTextField.selectAll();
            stringValueTextField.requestFocus();
        }
    }


    @Override
    public void commitEdit(String newValue) {
        if (stringValueTextField.getText().equalsIgnoreCase(""))
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
            AnalysisLimits item = getTableView().getItems().get(k);
            TablePosition position=getTableView().getSelectionModel().getSelectedCells().get(0);
            int col = position.getColumn();
            switch(col)
            {
                case 0:
                    item.setParam_name(newValue);
                    break;
                case 4:
                    item.setLabel(newValue);
                    break;
            }
            setText(stringValueTextField.getText());
            setGraphic(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        if (isEditing())
        {
            if (this.stringValueTextField != null)
                this.stringValueTextField.setText(null);
            setText(null);
            setGraphic(stringValueTextField);
        }
        else
        {
            setText(getItem());
            setGraphic(null);
        }
    }


    private void createEntryBox()
    {
        stringValueTextField =new TextField();
        stringValueTextField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        stringValueTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    commitEdit(stringValueTextField.getText());
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
