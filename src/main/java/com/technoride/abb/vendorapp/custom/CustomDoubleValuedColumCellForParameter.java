package com.technoride.abb.vendorapp.custom;

import com.technoride.abb.vendorapp.entity.AnalysisLimits;
import com.technoride.abb.vendorapp.entity.Parameter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

public class CustomDoubleValuedColumCellForParameter extends TableCell<AnalysisLimits,Double>
{

        private TextField doubleValueTextBox;

        @Override
        public void startEdit() {
            if (!isEmpty())
            {
                super.startEdit();
                createEntryBox();
                setText(null);
                setGraphic(doubleValueTextBox);
                doubleValueTextBox.selectAll();
                doubleValueTextBox.requestFocus();
            }
        }


        @Override
        public void commitEdit(Double newValue) {
            if (doubleValueTextBox.getText().equalsIgnoreCase(""))
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
                    case 1:
                        item.setCenter(newValue);
                        break;
                    case 2:
                        item.setWarningpct(newValue);
                        break;
                    case 3:
                        item.setErrorpct(newValue);
                        break;
                }
                setText(doubleValueTextBox.getText());
                setGraphic(null);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
        }

        @Override
        protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
            {
                setText(null);
                setGraphic(null);
            }
            else
            if (isEditing())
            {
                if (this.doubleValueTextBox != null)
                    this.doubleValueTextBox.setText(null);
                setText(null);
                setGraphic(doubleValueTextBox);
            }
            else
            {
                setText(String.valueOf(getItem()));
                setGraphic(null);
            }
        }


        private void createEntryBox()
        {
            doubleValueTextBox=new TextField(getText());
            doubleValueTextBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            doubleValueTextBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue) {
                        commitEdit(Double.parseDouble(doubleValueTextBox.getText()));
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


