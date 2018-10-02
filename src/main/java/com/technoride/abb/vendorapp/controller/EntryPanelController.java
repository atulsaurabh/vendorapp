package com.technoride.abb.vendorapp.controller;


import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;


@Controller

public class EntryPanelController
{

    private Stage window;
    private static boolean WINDOW_FULL_SCREEN;

    @FXML
    private Label currentTimePane;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTimePicker timePicker;


    @FXML
    private JFXTextField testingAuthority;




    @FXML
    public void initialize() {
        WINDOW_FULL_SCREEN = true;
        LocalDate localDate = LocalDate.now();

        datePicker.setValue(LocalDate.now());
        //timePicker.setValue(LocalTime.now());
        timePicker.setIs24HourView(true);
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
        timePicker.valueProperty().bind(timeTask.valueProperty());
       Thread thread = new Thread(timeTask);
       thread.setDaemon(true);
       thread.start();

    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    @FXML
    public void closeTheWindow(MouseEvent event) {
        currentTimePane.textProperty().unbind();
        timePicker.valueProperty().unbind();
        testingAuthority.textProperty().unbind();
        window.close();
    }

    @FXML
    public void maximizeTheWindow(MouseEvent mouseEvent)
    {
        if (mouseEvent.getButton() == MouseButton.PRIMARY || mouseEvent.isMetaDown())
        {
            if(mouseEvent.getClickCount() == 2)
            {
                if (WINDOW_FULL_SCREEN)
                {
                    WINDOW_FULL_SCREEN=false;
                    window.setFullScreen(false);
                    //window.setMaximized(true);
                }
                else
                {
                    WINDOW_FULL_SCREEN=true;
                    //window.setMaximized(false);
                    window.setFullScreen(true);
                }
            }
        }
    }
}
