package de.ora.gaston.core;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.DisconnectEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.List;
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
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        final User author = event.getUser();
        if (author.isBot()) {
            return;
        }

        String joinMessage = "Hallo %user%! Willkommen auf %guild%!";
        if (joinMessage.equals("0")) {
            return;
        } else {
            final Guild guild = event.getGuild();
            TextChannel messageChannel = guild.getDefaultChannel();
            if (messageChannel == null)
                return;
            final Member member = event.getMember();
            joinMessage = joinMessage.replace("%user%", member.getAsMention());
            joinMessage = joinMessage.replace("%guild%", guild.getName());
            messageChannel.sendMessage(joinMessage).queue();
        }
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final User author = event.getAuthor();
        if (author.isBot()) {
            return;
        }
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!metta")) {
            final MessageChannel channel = event.getChannel();
            String message = "Hallo %user%! Willkommen auf %guild%. Stell dich doch kurz auf %introchannel% vor";
            message = message.replace("%user%", author.getAsMention());
            message = message.replace("%guild%", event.getGuild().getName());
            final List<TextChannel> channels = event.getGuild().getTextChannelsByName("vorstellung", true);
            message = message.replace("%introchannel%", channels.get(0).getAsMention());
            //channel.sendMessage("Hallo " + author.getAsMention() + "! Ich bin ein Bot.").queue();
            channel.sendMessage(message).queue();
        }
    }
}
