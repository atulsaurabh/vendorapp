package com.technoride.abb.vendorapp.controller;

import com.technoride.abb.vendorapp.custom.*;
import com.technoride.abb.vendorapp.entity.*;
import com.technoride.abb.vendorapp.loader.GUIInfo;
import com.technoride.abb.vendorapp.loader.VendorAppLoader;
import com.technoride.abb.vendorapp.loader.WindowAndController;
import com.technoride.abb.vendorapp.repository.VarientRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
    private ComboBox<ProductInfo> varient_product;

    @FXML
    private TableView<AnalysisLimits> paramTable;

    //@FXML
    //private TableColumn<AnalysisLimits, String> productCatColumn;

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

    @FXML
    private TableView<ProductVariantDetail> varianttable;

    @FXML
    private TableColumn<ProductVariantDetail, String> vcode;

    @FXML
    private TableColumn<ProductVariantDetail, Integer> stpos;

    @FXML
    private TableColumn<ProductVariantDetail, Integer> endpos;

    @FXML
    private TableColumn<ProductVariantDetail, String> barcode;



    @Autowired
    private VarientRepository varientRepository;
    @Autowired
    private VendorAppLoader loader;

    private static boolean flag=false;


    public void initialize()
    {
        paramTable.getSelectionModel().setCellSelectionEnabled(true);
        paramTable.setEditable(true);

      //  productCatColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
       // productCatColumn.setCellFactory(param -> {
         //  return new CustomStringValueColumnCell();
        //});
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


        varianttable.getSelectionModel().setCellSelectionEnabled(true);
        varianttable.setEditable(true);

        vcode.setCellValueFactory(new PropertyValueFactory<>("variantcode"));
        vcode.setCellFactory(param -> {
            return new CustomStringValuedColumCellForVariant();
        });
        stpos.setCellValueFactory(new PropertyValueFactory<>("startpos"));
        stpos.setCellFactory(param -> new CustomIntegerValuedColumnCellFroVariant());
        endpos.setCellValueFactory(new PropertyValueFactory<>("endpos"));
        endpos.setCellFactory(param -> new CustomIntegerValuedColumnCellFroVariant());
        barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        barcode.setCellFactory(param -> new CustomStringValuedColumCellForVariant());
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


    public void fetchProductForVariant(ActionEvent actionEvent)
    {
        varient_product.getItems().clear();
        varient_product.getItems().addAll(varientRepository.getAllProductInfo());
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


    public void resetAllParameters(ActionEvent actionEvent)
    {
        paramTable.getItems().clear();
    }


    public void saveAll(ActionEvent actionEvent)
    {
        ProductInfo pInfo = productInfo.getSelectionModel().getSelectedItem();
        if (pInfo == null)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Choose product");
            alert.setHeaderText("Not selected");
            alert.setContentText("Kindly choose the product.\n If product is not available kindly press fetch button");
            alert.show();
            return;
        }
        if (paramTable.getItems().isEmpty())
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty");
            alert.setHeaderText("No data found");
            alert.setContentText("Add parameters to save");
            alert.show();
            return;
        }
        paramTable.getItems().stream().forEach(analysisLimits -> {
            analysisLimits.setCategory(pInfo.getProductcode());
        });
        if(varientRepository.addAllParameters(paramTable.getItems()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Batch update");
            alert.setHeaderText("Success");
            alert.setContentText("All parameters saved successfully");
            alert.show();
            paramTable.getItems().clear();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Batch update");
            alert.setHeaderText("Failure");
            alert.setContentText("All parameters not saved successfully");
            alert.show();
        }
    }


    public void createVarientRow(ActionEvent actionEvent)
    {
        ProductVariantDetail productVariantDetail = new ProductVariantDetail();
        varianttable.getItems().add(productVariantDetail);
    }


    public void resetVariant(ActionEvent actionEvent)
    {
        varianttable.getItems().clear();
    }


    public void saveVariants(ActionEvent actionEvent)
    {

        ProductInfo productInfo = varient_product.getSelectionModel().getSelectedItem();
        if (productInfo == null)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Choose product");
            alert.setHeaderText("Not selected");
            alert.setContentText("Kindly choose the product.\n If product is not available kindly press fetch button");
            alert.show();
            return;
        }
        ObservableList<ProductVariantDetail> productVariantDetails = varianttable.getItems();
        productVariantDetails.stream().forEach(productVariantDetail -> {
            ProductVarient productVarient = new ProductVarient();
            productVarient.setProductcode(productInfo.getProductcode());
            productVarient.setVarientcode(productVariantDetail.getVariantcode());
            productVarient.setStartchar(productVariantDetail.getStartpos());
            productVarient.setEndchar(productVariantDetail.getEndpos());

            Varient varient = new Varient();
            varient.setBarcode_part(productVariantDetail.getBarcode());

            if (!varientRepository.addVariant(productVarient,varient))
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Variant Operation");
                alert.setHeaderText("Failure");
                alert.setContentText("Variant is not added");
                alert.show();
                flag=true;
                return;
            }

        });

        if (!flag)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Variant Operation");
            alert.setHeaderText("Success");
            alert.setContentText("Variants are added");
            alert.show();
        }

        flag=false;
        varianttable.getItems().clear();
        varient_product.getItems().clear();
    }


    public void openOverloadParamWindow(ActionEvent actionEvent)
    {
        WindowAndController windowAndController =  loader.load(GUIInfo.OVERLOAD_PARAM_SCREEN);
        Stage stage = new Stage();
        Scene scene = new Scene(windowAndController.getWindow());
        stage.setScene(scene);
        stage.show();
    }

}
