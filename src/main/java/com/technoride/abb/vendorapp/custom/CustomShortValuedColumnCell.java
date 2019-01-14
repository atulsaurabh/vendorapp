package com.technoride.abb.vendorapp.custom;

import com.technoride.abb.vendorapp.entity.AnalysisLimits;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

public class CustomShortValuedColumnCell extends TableCell<AnalysisLimits,Short>
{
    private TextField shortValueTextBox;


    @Override
    public void startEdit() {
        if (!isEmpty())
        {
            super.startEdit();
            createEntryBox();
            setText(null);
            setGraphic(shortValueTextBox);
            shortValueTextBox.selectAll();
            shortValueTextBox.requestFocus();
        }
    }


    @Override
    public void commitEdit(Short newValue) {
        if (shortValueTextBox.getText().equalsIgnoreCase(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Value required.");
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
                case 6:
                    item.setVariable_limits(newValue);
                    break;
            }
            setText(shortValueTextBox.getText());
            setGraphic(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    protected void updateItem(Short item, boolean empty) {
        super.updateItem(item, empty);
        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        if (isEditing())
        {
            if (this.shortValueTextBox != null)
                this.shortValueTextBox.setText(null);
            setText(null);
            setGraphic(shortValueTextBox);
        }
        else
        {
            setText(String.valueOf(getItem()));
            setGraphic(null);
        }
    }


    private void createEntryBox()
    {
        shortValueTextBox =new TextField();
        shortValueTextBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        shortValueTextBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    commitEdit(Short.parseShort(shortValueTextBox.getText()));
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
