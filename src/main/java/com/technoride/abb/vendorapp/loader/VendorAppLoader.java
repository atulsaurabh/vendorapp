package com.technoride.abb.vendorapp.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VendorAppLoader
{

    @Autowired
    private ApplicationContext context;



    public final WindowAndController load(String guiName)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(context::getBean);
            loader.setLocation(getClass().getResource(GUIInfo.GUI_HOME+guiName));
            Parent parent = loader.load();
            Object controller = loader.getController();

            return new WindowAndController(parent,controller);
        }
        catch (Exception ex)
        {
            Logger.getLogger(VendorAppLoader.class.getName()).log(Level.SEVERE,"Unable to load gui",ex);
        }
        return null;
    }
}
