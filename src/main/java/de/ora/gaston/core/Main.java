package de.ora.gaston.core;

import net.dv8tion.jda.client.events.group.GroupJoinEvent;
import net.dv8tion.jda.client.events.group.GroupUserJoinEvent;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.DisconnectEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Main extends ListenerAdapter {
    private static final Logger LOG = Logger.getLogger("Gaston");

    public static void main(String[] args) throws LoginException {
        ResourceBundle bundle = ResourceBundle.getBundle("app.config");
        final String token = bundle.getString("token");

        final JDA jda = new JDABuilder(AccountType.BOT)
                .addEventListener(new Main())
                .setToken(token)
                .buildAsync();



    }

    @Override
    public void onReady(ReadyEvent event) {
        LOG.info("Ready");
    }

    @Override
    public void onDisconnect(DisconnectEvent event) {
        LOG.info("Disconnected " + event.getDisconnectTime());
    }

    @Override
    public void onGroupJoin(GroupJoinEvent event) {
        super.onGroupJoin(event);
    }

    @Override
    public void onGroupUserJoin(GroupUserJoinEvent event) {
        super.onGroupUserJoin(event);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!metta")) {
            event.getChannel().sendMessage("Hallo!").queue();
        }
    }
}
