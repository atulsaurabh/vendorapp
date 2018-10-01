package com.technoride.abb.vendorapp;

import com.technoride.abb.vendorapp.configuration.BaseConfiguration;
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

public class VendorApp extends Application
{


    @Override
    public void start(Stage primaryStage) throws Exception {
        AnnotationConfigApplicationContext applicationContext =
                                   new AnnotationConfigApplicationContext(BaseConfiguration.class);

        VendorAppLoader loader = (VendorAppLoader) applicationContext.getBean("vendorAppLoader");
        WindowAndController windowAndController = loader.load(GUIInfo.LOADER_SCREEN);
        MainScreenLoaderControoler controoler = (MainScreenLoaderControoler)windowAndController.getController();
        controoler.setWindow(windowAndController.getWindow());
        Scene primaryScene = new Scene(windowAndController.getWindow());
        primaryStage.setScene(primaryScene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String [] args)
    {
        launch(args);
    }
}
