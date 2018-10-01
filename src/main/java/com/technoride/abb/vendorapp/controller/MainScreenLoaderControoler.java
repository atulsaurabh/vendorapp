package com.technoride.abb.vendorapp.controller;

import com.technoride.abb.vendorapp.loader.GUIInfo;
import com.technoride.abb.vendorapp.loader.VendorAppLoader;
import com.technoride.abb.vendorapp.loader.WindowAndController;
import com.technoride.abb.vendorapp.task.InitialTask;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MainScreenLoaderControoler implements Initializable {
    @FXML
    private ProgressBar initaltaskProgressbar;
    @FXML
    private Label message;

    @Autowired
    private InitialTask initialTask;

    @Autowired
    private VendorAppLoader vendorAppLoader;

    private Parent window;

    public void setWindow(Parent window) {
        this.window = window;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initaltaskProgressbar.setProgress(0);
        message.textProperty().bind(initialTask.messageProperty());
        //InitialTask initialTask = new InitialTask();
        initialTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event)
            {
                if(initialTask.isDone())
                {
                    message.textProperty().unbind();
                    message.setText("Done");
                    ((Stage)window.getScene().getWindow()).close();
                    Stage stage = new Stage();
                    WindowAndController windowAndController = vendorAppLoader.load(GUIInfo.LOGIN_SCREEN);
                    Scene scene = new Scene(windowAndController.getWindow());
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }

            }
        });

        initaltaskProgressbar.progressProperty().unbind();
        initaltaskProgressbar.progressProperty().bind(initialTask.progressProperty());
        //initaltaskProgressbar.set
        new Thread(initialTask).start();
    }
}
