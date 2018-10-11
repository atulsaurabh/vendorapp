package com.technoride.abb.vendorapp.controller;

import com.technoride.abb.vendorapp.entity.ProductInfo;
import com.technoride.abb.vendorapp.repository.ProductRepository;
import com.technoride.abb.vendorapp.util.Util;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BarcodeScannerController {

    private Stage stage;

    @FXML
    private TextArea barcode;

    @FXML
    private TextField ordercode;

    @FXML
    private TextField serialnumber;


    private EntryPanelController entryPanelController;

    @Autowired
    private ProductRepository productRepository;


    @FXML
    public void closeWindow(MouseEvent event) {
        stage.close();
    }



    @Autowired
    private Util util;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setEntryPanelController(EntryPanelController entryPanelController) {
        this.entryPanelController = entryPanelController;
    }

    @FXML
    public void prepareForReScan(ActionEvent event)
    {
        barcode.setEditable(true);
        barcode.setText("");
        ordercode.setText("");
        serialnumber.setText("");
        barcode.requestFocus();
    }

    @FXML
    public void editBarcodeEntry(ActionEvent actionEvent)
    {

        ordercode.setEditable(true);
        serialnumber.setEditable(true);
        ordercode.requestFocus();
    }

    @FXML
    public void commitChangeAndExit(ActionEvent actionEvent)
    {
        ordercode.setEditable(false);
        serialnumber.setEditable(false);
        ProductInfo productInfo=productRepository.getProductInfo(ordercode.getText());
        productInfo.setSerialnumber(serialnumber.getText());
        entryPanelController.setProductInfo(productInfo);
        entryPanelController.populateEntryForm(productInfo.getProductcode());
        stage.close();
    }

    @FXML
    public void countAndReact(KeyEvent event)
    {
         String barcodedata = barcode.getText();
         if(barcodedata.length() == util.findBarcodeLength())
         {
             String order_code = barcodedata.substring(0,15);
             String serial_code = barcodedata.substring(15);
             ordercode.setText(order_code);
             serialnumber.setText(serial_code);
             barcode.setEditable(false);
         }

    }

}
