package com.technoride.abb.vendorapp.controller;


import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.technoride.abb.vendorapp.entity.*;
import com.technoride.abb.vendorapp.loader.GUIInfo;
import com.technoride.abb.vendorapp.loader.VendorAppLoader;
import com.technoride.abb.vendorapp.loader.WindowAndController;
import com.technoride.abb.vendorapp.repository.OverloadParameterRepository;
import com.technoride.abb.vendorapp.repository.ProductRepository;
import com.technoride.abb.vendorapp.repository.ProductVarientRepository;
import com.technoride.abb.vendorapp.repository.VarientRepository;
import com.technoride.abb.vendorapp.util.Util;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;


@Controller

public class EntryPanelController
{

    private Stage window;


    @FXML
    private TextField barcode;

    @FXML
    private TextField category;

    @FXML
    private TextField subcategory;

    @FXML
    private Label currentTimePane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField serialnumber;

    @FXML
    private TextField testingAuthority;
    @FXML
    private TableView<Parameter> parameters_table;
    @FXML
    private TableColumn<?, ?> param_name;

    @FXML
    private TableColumn<?, ?> minValue;

    @FXML
    private TableColumn<?, ?> maxValue;

    @FXML
    private TableColumn<?, ?> testValue;

    @FXML
    private TableColumn<?, ?> comment;

    @FXML
    private Label testresult;

    @Autowired
    private Util util;


    @Autowired
    private VendorAppLoader loader;


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVarientRepository productVarientRepository;

    @Autowired
    private VarientRepository varientRepository;
    @Autowired
    private OverloadParameterRepository overloadParameterRepository;
    private List<Parameter> parameters;

    private LocalDateTime startTime;
    private String dut;

    private String codes;
    @FXML
    public void initialize() {


        LocalDate localDate = LocalDate.now();

        datePicker.setValue(LocalDate.now());
        //timePicker.setValue(LocalTime.now());
        testingAuthority.textProperty().bind(LoggedInUser.userProperty);
        Task<LocalTime> timeTask = new Task<LocalTime>() {
            @Override
            protected LocalTime call() throws Exception {
                while (true)
                {
                    try {
                        LocalTime time = LocalTime.now();
                        String timeFormat = time.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
                        updateMessage(timeFormat);
                        updateValue(time);
                        Thread.sleep(1);
                    }
                    catch (InterruptedException in)
                    {

                    }

                }
            }
        };

        currentTimePane.textProperty().bind(timeTask.messageProperty());
       Thread thread = new Thread(timeTask);
       thread.setDaemon(true);
       thread.start();

    }


    public void setWindow(Stage window) {
        this.window = window;
    }




    @FXML
    public void openBarCodeScannerWindow(ActionEvent event)
    {
        startTime=LocalDateTime.now();
        WindowAndController windowAndController=loader.load(GUIInfo.BARCODE_SCANNING_WINDOW);
        BarcodeScannerController controller = (BarcodeScannerController)windowAndController.getController();
        controller.setEntryPanelController(this);
        Stage stage = new Stage();
        controller.setStage(stage);
        Scene scene = new Scene(windowAndController.getWindow());
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    public void openProductDetailWindow(ActionEvent actionEvent)
    {
       WindowAndController windowAndController = loader.load(GUIInfo.VERIENT_ADD_SCREEN);
       Stage stage = new Stage();
       Scene scene = new Scene(windowAndController.getWindow());
       stage.setScene(scene);
       stage.show();


    }





    public void setProductInfo(ProductInfo info)
    {
       barcode.setText(info.getBarcode());
       category.setText(info.getProductcode());
        this.dut=info.getSerialnumber();
        serialnumber.setText(info.getSerialnumber());
    }

    public void populateEntryForm(String productcode)
    {

        parameters= productRepository.getParamers(productcode);
        List<ProductVarient> productVarients = productVarientRepository.getProductVarient(productcode);
        if (!productVarients.isEmpty())
        {
            productVarients.stream().forEach(productVarient ->
            {
               List<Varient> varients=varientRepository.getVarientFromId(productVarient.getId());
               Optional<Varient> dbvarient=varients.stream().filter(varient -> {
                   String bar_part=barcode.getText().substring(productVarient.getStartchar(),
                           productVarient.getEndchar()+1);
                   return bar_part.equals(varient.getBarcode_part());
               }).findFirst();
               if (dbvarient.isPresent())
               {
                   subcategory.setText(productVarient.getVarientcode());
                  List<Parameter> overloadedParameterList=overloadParameterRepository.getOverloadedParameter(dbvarient.get().getId());
                  if (!overloadedParameterList.isEmpty())
                  {
                      List<Parameter> newParameters = new ArrayList<>();
                      newParameters.addAll(parameters);
                      newParameters.stream().forEach(parameter -> {
                          overloadedParameterList.stream().forEach(overloadedParameter->{
                              synchronized (overloadedParameter) {
                                  if (overloadedParameter.getParametername().equalsIgnoreCase(parameter.getParametername())) {
                                      parameter.setErrorrange(overloadedParameter.getErrorrange());
                                      parameter.setCenter(overloadedParameter.getCenter());
                                      parameter.setWarningrange(overloadedParameter.getWarningrange());
                                  }
                              }
                          });
                      });
                  }
               }

            });
        }
        param_name.setCellValueFactory(new PropertyValueFactory<>("parametername"));
        minValue.setCellValueFactory(new PropertyValueFactory<>("minvalue"));
        maxValue.setCellValueFactory(new PropertyValueFactory<>("maxvalue"));
        testValue.setCellValueFactory(new PropertyValueFactory<>("testValue"));
        parameters_table.setRowFactory(tv -> new TableRow(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                Parameter parameter = (Parameter)item;
                if (parameter!= null && parameter.isFail())
                    setStyle("-fx-background-color: orange");
                else
                    setStyle("-fx-background-color: default");




            }
        });
        parameters_table.getItems().addAll(parameters);

    }

    @FXML
    public void submitResult(ActionEvent actionEvent)
    {
        if(parameters == null || parameters.isEmpty())
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Kindly populate the form by pressing on BARCODE button");
            alert.setHeaderText("Parameters not available");
            alert.showAndWait();
        }
        else
        {
            TestRecord testRecord = new TestRecord();
            testRecord.setDut(dut);
            testRecord.setCategory(category.getText());
            testRecord.setOrdercode(barcode.getText());
            testRecord.setStarttime(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            testRecord.setResult((testresult.getText().equals("PASS")?(short) 1:(short) 0));
            testRecord.setTestStation(util.findTestStationName());
            testRecord.setResultcode((short)0);
            testRecord.setUserdesc(testingAuthority.getText());
            WindowAndController windowAndController = loader.load(GUIInfo.ERROR_CLASS_SELECTOR_WINDOW);
            Stage stage= new Stage();
            ErrorClassSelectorController controller=(ErrorClassSelectorController)windowAndController.getController();
            controller.setStage(stage);
            controller.setStartTime(startTime);
            controller.setParameters(parameters);
            controller.setTestRecord(testRecord);
            stage.setScene(new Scene(windowAndController.getWindow()));
            stage.show();

        }
    }
}
