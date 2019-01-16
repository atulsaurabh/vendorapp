package com.technoride.abb.vendorapp.custom;

import com.technoride.abb.vendorapp.entity.AnalysisLimits;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

public class CustomBooleanValuedColumnCellForParameter extends TableCell<AnalysisLimits,Boolean>
{
    private CheckBox booleanValuedCheckbox;

    @Override
    public void startEdit() {
        if (!isEmpty())
        {
            super.startEdit();
            createEntryBox();
            setText(null);
            setGraphic(booleanValuedCheckbox);
            //booleanValuedCheckbox.selectAll();
            booleanValuedCheckbox.requestFocus();
        }
    }


    @Override
    public void commitEdit(Boolean newValue) {

            int k = getTableView().getSelectionModel().getFocusedIndex();
            AnalysisLimits item = getTableView().getItems().get(k);
            TablePosition position=getTableView().getSelectionModel().getSelectedCells().get(0);
            int col = position.getColumn();
            switch(col)
            {
                case 4:
                    item.setOverload(newValue);
                    break;

            }
            setText(null);
            setGraphic(null);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        if (isEditing())
        {
            setText(null);
            setGraphic(booleanValuedCheckbox);
        }
        else
        {
            setText(null);
            setGraphic(null);
        }
    }


    private void createEntryBox()
    {
        booleanValuedCheckbox =new CheckBox();
        booleanValuedCheckbox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        booleanValuedCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
            {
                commitEdit(booleanValuedCheckbox.isSelected());
                getTableView().getSelectionModel().selectNext();
                int currentColIndex = getTableView().getColumns().indexOf(getTableColumn());
                TableColumn nextCol = getTableView().getColumns().get(currentColIndex+1);
                if (nextCol != null)
                    getTableView().edit(getTableRow().getIndex(), nextCol);

            }
        });
    }
}
