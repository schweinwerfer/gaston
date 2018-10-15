package de.ora.gaston.core;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        ResourceBundle bundle = ResourceBundle.getBundle("app.config");
        final String token = bundle.getString("token");
        builder.setToken(token);
    }
}
