package com.technoride.abb.vendorapp.controller;

import com.technoride.abb.vendorapp.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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

    @FXML
    void performLogin(ActionEvent event) {
        resetMessage();
        hasError=false;
        validate();
        if(!hasError)
        {
          if (userRepository.login(userName.getText(),password.getText()))
          {
              System.out.println("Login Successful");
          }
          else
          {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setContentText("Either <user name,password> pair is invalid " +
                      "\n Or account is inactive" +
                      "\nKindly consult the administrator to unlock the account\n" +
                      "in case it is inactive");
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
