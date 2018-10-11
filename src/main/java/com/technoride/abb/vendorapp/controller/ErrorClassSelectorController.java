package com.technoride.abb.vendorapp.controller;

import com.jfoenix.controls.JFXComboBox;
import com.technoride.abb.vendorapp.entity.ErrorClass;
import com.technoride.abb.vendorapp.entity.Parameter;
import com.technoride.abb.vendorapp.entity.TestRecord;
import com.technoride.abb.vendorapp.entity.TestResult;
import com.technoride.abb.vendorapp.repository.ErrorClassRepository;
import com.technoride.abb.vendorapp.repository.TestRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Controller
@Scope("prototype")
public class ErrorClassSelectorController {
    private Stage stage;

    private LocalDateTime startTime;

    private TestRecord testRecord;

    private List<Parameter> parameters;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void setTestRecord(TestRecord testRecord) {
        this.testRecord = testRecord;
    }

    @FXML
    private ComboBox<ErrorClass> errorclass;

    @FXML
    private CheckBox include;

    @FXML
    public void closeWindow(MouseEvent event) {
        stage.close();

    }

    @Autowired
    private TestRepository errorClassRepository;

    @FXML
    public void initialize()
    {
       List<ErrorClass> errorClasses=errorClassRepository.errorClass();
       Callback<ListView<ErrorClass>,ListCell<ErrorClass>> factory = lv -> new ListCell<ErrorClass>()
       {
           @Override
           protected void updateItem(ErrorClass item, boolean empty) {
               super.updateItem(item, empty);
               setText(empty ? "":item.getErrorclassname());
           }

       };
       errorclass.setCellFactory(factory);
       errorclass.getItems().addAll(errorClasses);
    }


    @FXML
    public void updateToDB(ActionEvent event)
    {
        LocalDateTime endTime = LocalDateTime.now();
        testRecord.setEndtime(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        testRecord.setIncludeinreport(include.isSelected() ? (short)1 : (short)0);
        ErrorClass selectedErrorClass=errorclass.getSelectionModel().getSelectedItem();
        testRecord.setErrorcategory(selectedErrorClass.getErrorcode());
        List<TestResult> testResults = new ArrayList<>();
        for (Parameter p : parameters)
        {
            TestResult ts = new TestResult();
            ts.setParam_name(p.getParametername());
            if (p.getTesttype().equals("TEXT")) {
                ts.setParamvalue(p.getTextTestResult());
                if(p.getTextTestResult() < p.getMinvalue() || p.getTextTestResult() > p.getMaxvalue())
                    ts.setResult((short)0);
                else
                    ts.setResult((short)1);
            }
            else
            {
                double d = (p.getBooleanTestResult()?1.0: 0.0);
                ts.setParamvalue(d);
                if (p.getBooleanTestResult() == Boolean.TRUE)
                    ts.setResult((short)1);
                else
                    ts.setResult((short)0);
            }

            testResults.add(ts);
        }

      if(errorClassRepository.insertTestRecoder(testRecord,testResults))
      {
          Alert alert=new Alert(Alert.AlertType.INFORMATION);
          alert.setContentText("Test Record Inserted Successfully");
          alert.setHeaderText("Success");
          alert.showAndWait();
      }
      else
      {
          Alert alert=new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Test Record Not Inserted Successfully");
          alert.setHeaderText("Failure");
          alert.showAndWait();
      }

    }


    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
