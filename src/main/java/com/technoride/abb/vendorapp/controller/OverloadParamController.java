package com.technoride.abb.vendorapp.controller;


import com.technoride.abb.vendorapp.custom.CustomBooleanValuedColumnCellForParameter;
import com.technoride.abb.vendorapp.custom.CustomDoubleValuedColumCellForParameter;
import com.technoride.abb.vendorapp.entity.*;
import com.technoride.abb.vendorapp.repository.VarientRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OverloadParamController {
    @FXML
    private ComboBox<ProductInfo> productInfoCombo;
    @FXML
    private ComboBox<ProductVarient> varientInfoCombo;

    @FXML
    private TableView<AnalysisLimits> overloadParamTable;

    @FXML
    private TableColumn<AnalysisLimits, String> paramnamecolumn;

    @FXML
    private TableColumn<AnalysisLimits, Double> centercolumn;

    @FXML
    private TableColumn<AnalysisLimits, Double> warningcolumn;

    @FXML
    private TableColumn<AnalysisLimits, Double> errorcolumn;
    @FXML
    private TableColumn<AnalysisLimits, Boolean> overloadCheckColumn;

    @Autowired
    private VarientRepository varientRepository;

    @FXML
    public void initialize() {

        overloadParamTable.setEditable(true);
        overloadParamTable.getSelectionModel().setCellSelectionEnabled(true);
        paramnamecolumn.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        centercolumn.setCellValueFactory(new PropertyValueFactory<>("center"));
        centercolumn.setCellFactory(param -> {
            return new CustomDoubleValuedColumCellForParameter();
        });
        warningcolumn.setCellValueFactory(new PropertyValueFactory<>("warningpct"));
        warningcolumn.setCellFactory(param -> {
            return new CustomDoubleValuedColumCellForParameter();
        });
        errorcolumn.setCellValueFactory(new PropertyValueFactory<>("errorpct"));
        errorcolumn.setCellFactory(param -> new CustomDoubleValuedColumCellForParameter());

        overloadCheckColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        productInfoCombo.getItems().addAll(varientRepository.getAllProductInfo());
        productInfoCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            List<AnalysisLimits> parameters = varientRepository.getAllParameterFromProductCode(newValue.getProductcode().trim());
            overloadParamTable.getItems().clear();
            overloadParamTable.getItems().addAll(parameters);
            List<ProductVarient> productVarients = varientRepository.getAllProductVarientFromProductCode(newValue.getProductcode());
            varientInfoCombo.getItems().clear();
            varientInfoCombo.getItems().addAll(productVarients);
        });


    }


    public void saveOverloadParameters(ActionEvent actionEvent)
    {
        ProductVarient productVarient= varientInfoCombo.getSelectionModel().getSelectedItem();
        if (productVarient == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select");
            alert.setHeaderText("No variant selected");
            alert.setContentText("Kindly select a variant from the variant list");
            alert.show();
            return;
        }
        List<AnalysisLimits> overloaded=overloadParamTable.getItems().stream().filter(analysisLimits -> {
            return analysisLimits.isOverload();
        }).collect(Collectors.toList());

        if (varientRepository.addAllOverloadedVarient(overloaded,productVarient.getId()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update result");
            alert.setHeaderText("Success");
            alert.setContentText("All overloaded parameter saved");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update result");
            alert.setHeaderText("Failure");
            alert.setContentText("Overloaded parameter not saved");
            alert.show();
        }

    }

    public void resetTable(ActionEvent actionEvent)
    {
        overloadParamTable.getItems().clear();
    }
}
