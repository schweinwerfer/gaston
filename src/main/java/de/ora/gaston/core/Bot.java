package de.ora.gaston.core;

import de.ora.gaston.command.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.DisconnectEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.update.GuildUpdateAfkTimeoutEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Bot extends ListenerAdapter {
    private static final Logger LOG = Logger.getLogger(Bot.class.getSimpleName());
    private static final Map<CommandMeta, BotCommand> availableCommands;
    private static final HelpCmd HELP_CMD = new HelpCmd();
    private JDA jda;

    static {
        availableCommands = new HashMap<>();
        availableCommands.put(CommandMeta.VERSION, new VersionCmd());
        availableCommands.put(CommandMeta.HELP, new HelpCmd());
        availableCommands.put(CommandMeta.INTRO, new WelcomeCmd("willkommen", "vorstellung"));
    }

    public Bot() {
    }

    @Override
    public void onReady(ReadyEvent event) {
        LOG.info("Ready");
        this.jda = event.getJDA();
        LOG.info("Max reconnect delay: " + jda.getMaxReconnectDelay());
        LOG.info("Ping: " + jda.getPing());
    }

    @Override
    public void onGuildUpdateAfkTimeout(GuildUpdateAfkTimeoutEvent event) {
        super.onGuildUpdateAfkTimeout(event);
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

        new WelcomeCmd("willkommen", "vorstellung").perform(event);
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final User author = event.getAuthor();
        if (author.isBot()) {
            return;
        }
        final String contentRaw = event.getMessage().getContentRaw();
        final CommandMeta commandMeta = CommandMeta.lookup(contentRaw);

        final BotCommand botCommand = availableCommands.get(commandMeta);
        if (botCommand != null) {
            botCommand.perform(event);
        } else {
            HELP_CMD.perform(event);
        }
    }

}
