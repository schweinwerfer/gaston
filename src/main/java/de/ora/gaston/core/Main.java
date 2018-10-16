package de.ora.gaston.core;

import de.ora.gaston.util.Config;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    private static JDA jda;

    public static void main(String[] args) throws LoginException {

        final String token = Config.getToken();

        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                jda.shutdown();
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        jda = new JDABuilder(AccountType.BOT)
                .addEventListener(new Bot())
                .setToken(token)
                .buildAsync();
    }

}
