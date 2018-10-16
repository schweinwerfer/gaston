package de.ora.gaston.util;

import java.util.ResourceBundle;

public class Config {
    private static final ResourceBundle config = ResourceBundle.getBundle("app.config");

    public static String getToken() {
        return config.getString("token");
    }

    public static String getVersion() {
        return config.getString("version");
    }
}
