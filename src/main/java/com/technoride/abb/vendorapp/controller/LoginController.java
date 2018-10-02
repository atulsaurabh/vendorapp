package com.technoride.abb.vendorapp.controller;

import com.technoride.abb.vendorapp.loader.GUIInfo;
import com.technoride.abb.vendorapp.loader.VendorAppLoader;
import com.technoride.abb.vendorapp.loader.WindowAndController;
import com.technoride.abb.vendorapp.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

@Controller
public class LoginController
{

    @Autowired
    private UserRepository userRepository;
    @FXML
    private JFXTextField userName;
    @FXML
    private Label userNameError;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label passwordError;

    private boolean hasError;

    private Stage window;

    @Autowired
    private VendorAppLoader loader;

    public void setWindow(Stage window) {
        this.window = window;
    }

    @FXML
    void performLogin(ActionEvent event) {
        resetMessage();
        hasError=false;
        validate();
        if(!hasError)
        {
          if (userRepository.login(userName.getText(),password.getText()))
          {
              WindowAndController windowAndController = loader.load(GUIInfo.ENTRY_SCREEN);
              Scene scene = new Scene(windowAndController.getWindow());
              EntryPanelController controller=(EntryPanelController) windowAndController.getController();
              LoggedInUser.setUser(userName.getText());
              Stage stage = new Stage();
              controller.setWindow(stage);
              stage.setScene(scene);
              stage.setFullScreen(true);
              stage.setAlwaysOnTop(true);
              stage.initStyle(StageStyle.UNDECORATED);
              window.close();
              stage.show();
          }
          else
          {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setContentText("Either <user name,password> pair is invalid Or account is inactive."+
                      "\nKindly consult the administrator to unlock the account in case it is inactive");
              alert.setHeaderText("Login Unsuccessful");
              alert.showAndWait();
          }
        }

    }

    @FXML
    void resetData(ActionEvent event) {
      userName.setText("");
      password.setText("");
      resetMessage();
    }

    private void resetMessage()
    {
        userNameError.setText("");
        passwordError.setText("");
    }

    private void validate()
    {
        if(userName.getText().trim().equals(""))
        {
            userNameError.setText("User name can not be blank");
            hasError=true;
        }
        if (password.getText().trim().equals(""))
        {
            passwordError.setText("Password can not be blank");
            hasError=true;
        }

    }

}
