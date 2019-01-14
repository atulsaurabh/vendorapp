package com.technoride.abb.vendorapp.controller;

import com.technoride.abb.vendorapp.custom.CustomDoubleValuedColumnCell;
import com.technoride.abb.vendorapp.custom.CustomShortValuedColumnCell;
import com.technoride.abb.vendorapp.custom.CustomStringValueColumnCell;
import com.technoride.abb.vendorapp.entity.AnalysisLimits;
import com.technoride.abb.vendorapp.entity.ProductInfo;
import com.technoride.abb.vendorapp.repository.VarientRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductDetailController
{
    @FXML
    private TextField productCode;
    @FXML
    private TextField productName;
    @FXML
    private TextField productCatCode;
    @FXML
    private TextField orderCode;
    @FXML
    private ComboBox<ProductInfo> productInfo;


    @FXML
    private TableView<AnalysisLimits> paramTable;

    @FXML
    private TableColumn<AnalysisLimits, String> productCatColumn;

    @FXML
    private TableColumn<AnalysisLimits, String> paramNameColumn;

    @FXML
    private TableColumn<AnalysisLimits, Double> centerColumn;

    @FXML
    private TableColumn<AnalysisLimits, Double> warnPctColumn;

    @FXML
    private TableColumn<AnalysisLimits, Double> errorPctColumn;

    @FXML
    private TableColumn<AnalysisLimits, String> labelColumn;

    @FXML
    private TableColumn<AnalysisLimits, Short> vLimitColumn;

    @FXML
    private TableColumn<AnalysisLimits, Double> lclColumn;

    @FXML
    private TableColumn<AnalysisLimits, Double> uclColumn;


    @Autowired
    private VarientRepository varientRepository;



    public void initialize()
    {
        paramTable.getSelectionModel().setCellSelectionEnabled(true);
        paramTable.setEditable(true);

        productCatColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        productCatColumn.setCellFactory(param -> {
           return new CustomStringValueColumnCell();
        });
        paramNameColumn.setCellValueFactory(new PropertyValueFactory<>("param_name"));
        paramNameColumn.setCellFactory(param -> {
            return new CustomStringValueColumnCell();
        });
        centerColumn.setCellValueFactory(new PropertyValueFactory<>("center"));
        centerColumn.setCellFactory(param -> {
            return new CustomDoubleValuedColumnCell();
        });
        warnPctColumn.setCellValueFactory(new PropertyValueFactory<>("warningpct"));
        warnPctColumn.setCellFactory(param -> {
            return new CustomDoubleValuedColumnCell();
        });
        errorPctColumn.setCellValueFactory(new PropertyValueFactory<>("errorpct"));
        errorPctColumn.setCellFactory(param -> {
            return new CustomDoubleValuedColumnCell();
        });
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        labelColumn.setCellFactory(param -> {
            return new CustomStringValueColumnCell();
        });
        vLimitColumn.setCellValueFactory(new PropertyValueFactory<>("variable_limits"));
        vLimitColumn.setCellFactory(param -> {
            return new CustomShortValuedColumnCell();
        });
        lclColumn.setCellValueFactory(new PropertyValueFactory<>("lcl"));
        lclColumn.setCellFactory(param -> {
            return new CustomDoubleValuedColumnCell();
        });
        uclColumn.setCellValueFactory(new PropertyValueFactory<>("ucl"));
        uclColumn.setCellFactory(param -> {
            return new CustomDoubleValuedColumnCell();
        });
    }


    public void saveProduct(ActionEvent actionEvent)
    {
     Object []  parameters = new Object[]{
       productCode.getText(),
       productName.getText(),
       productCatCode.getText(),
       orderCode.getText()
     };

     if (varientRepository.addProduct(parameters))
     {
         Alert alert=new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText("Product added successfully");
         alert.setHeaderText("Success");
         alert.setTitle("Product result");
         alert.show();
         resetProduct();
     }
     else
     {
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setContentText("Product not added successfully");
         alert.setHeaderText("Failure");
         alert.setTitle("Product result");
         alert.show();
         resetProduct();
     }

    }


    public void resetProductForm(ActionEvent actionEvent)
    {
        resetProduct();
    }


    private void resetProduct()
    {
        productCode.setText("");
                productName.setText("");
                productCatCode.setText("");
                orderCode.setText("");
    }



    public void fetchAllProducts(ActionEvent actionEvent)
    {
        productInfo.getItems().clear();
        productInfo.getItems().addAll(varientRepository.getAllProductInfo());
    }


    public void createNewParameter(ActionEvent actionEvent)
    {
        AnalysisLimits analysisLimits = new AnalysisLimits();
        paramTable.getItems().add(analysisLimits);
    }


    public void deleteSelectedParam(ActionEvent actionEvent)
    {
        AnalysisLimits analysisLimits=paramTable.getFocusModel().getFocusedItem();
        paramTable.getItems().remove(analysisLimits);
    }

}
