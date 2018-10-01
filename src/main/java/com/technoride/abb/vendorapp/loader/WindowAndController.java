package com.technoride.abb.vendorapp.loader;

import javafx.scene.Parent;

public class WindowAndController {
    private Parent window;
    private Object controller;

    public WindowAndController() {
    }

    public WindowAndController(Parent window, Object controller) {
        this.window = window;
        this.controller = controller;
    }

    public Parent getWindow() {
        return window;
    }

    public void setWindow(Parent window) {
        this.window = window;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
