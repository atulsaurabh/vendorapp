package com.technoride.abb.vendorapp;

import com.technoride.abb.vendorapp.configuration.BaseConfiguration;
import com.technoride.abb.vendorapp.configuration.DbConfig;
import com.technoride.abb.vendorapp.controller.MainScreenLoaderControoler;
import com.technoride.abb.vendorapp.loader.GUIInfo;
import com.technoride.abb.vendorapp.loader.VendorAppLoader;
import com.technoride.abb.vendorapp.loader.WindowAndController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.List;

public class VendorApp extends Application
{


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parameters parameters=getParameters();
        List<String> commandLineParameters=parameters.getRaw();
        HashMap<String,String> cParameters = new HashMap<>();
        for (String commandLine : commandLineParameters)
        {
            commandLine = commandLine.substring(2);
            String [] keyValues = commandLine.split("=");
            if (keyValues.length != 2)
            {
                cParameters.put(keyValues[0],"");
            }
            else
            cParameters.put(keyValues[0],keyValues[1]);
        }

        DbConfig.dbparams=cParameters;

        if (!cParameters.containsKey("db.configdir"))
        {
            System.err.println("Configuration directory path is not available.");
            System.err.println("Use command line option --db.configdir to set the configuration directory");
            System.exit(-1);
        }



        AnnotationConfigApplicationContext applicationContext =
                                   new AnnotationConfigApplicationContext(BaseConfiguration.class);

        VendorAppLoader loader = (VendorAppLoader) applicationContext.getBean("vendorAppLoader");
        if (cParameters.containsKey("config"))
        {
            WindowAndController windowAndController = loader.load(GUIInfo.VERIENT_ADD_SCREEN);
            Scene primaryScene = new Scene(windowAndController.getWindow());
            primaryStage.setScene(primaryScene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        }
        else
        {
            WindowAndController windowAndController = loader.load(GUIInfo.LOADER_SCREEN);
            MainScreenLoaderControoler controler = (MainScreenLoaderControoler)windowAndController.getController();
            controler.setWindow(windowAndController.getWindow());
            Scene primaryScene = new Scene(windowAndController.getWindow());
            primaryStage.setScene(primaryScene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        }

    }


    public static void main(String [] args)
    {
        launch(args);
    }
}
