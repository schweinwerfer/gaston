package de.ora.gaston.core;

import de.ora.gaston.util.Config;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.DisconnectEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;
import java.util.logging.Logger;

public class Bot extends ListenerAdapter {
    private static final Logger LOG = Logger.getLogger(Bot.class.getSimpleName());
    private static final String VERSION_STRING_SHORT_TEMPLATE = "Metta Bot %s";


    public Bot() {
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

        final Guild guild = event.getGuild();
        final TextChannel messageChannel = guild.getDefaultChannel();
        if (messageChannel == null) {
            return;
        }
        final Member member = event.getMember();
        final TextChannel introChannel = getChannel(guild, "vorstellung");
        String message = welcomeMsg(guild, member, introChannel);
        messageChannel.sendMessage(message).queue();
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final User author = event.getAuthor();
        if (author.isBot()) {
            return;
        }
        final String contentRaw = event.getMessage().getContentRaw();
        if (contentRaw.equalsIgnoreCase("!version")) {
            final MessageChannel channel = event.getChannel();
            channel.sendMessage("Hallo " + author.getName() + "! Ich bin " + String.format(VERSION_STRING_SHORT_TEMPLATE, Config.getVersion())).queue();
        } else if (contentRaw.equalsIgnoreCase("!intro")) {
            final MessageChannel channel = event.getChannel();
            final Guild guild = event.getGuild();
            final TextChannel introChannel = getChannel(guild, "vorstellung");
            String message = welcomeMsg(guild, author, introChannel);
            channel.sendMessage(message).queue();
        }
    }

    private TextChannel getChannel(final Guild guild, final String channelName) {
        final List<TextChannel> matchingChannels = guild.getTextChannelsByName(channelName, true);
        if (matchingChannels == null || matchingChannels.isEmpty()) {
            return null;
        }
        return matchingChannels.get(0);
    }

    private String welcomeMsg(final Guild guild, final IMentionable member, final TextChannel introChannel) {
        String message = "Hallo %user%! Willkommen auf %guild%. Stell dich doch kurz auf %introchannel% vor";
        message = message.replace("%user%", member.getAsMention());
        message = message.replace("%guild%", guild.getName());
        message = message.replace("%introchannel%", introChannel.getAsMention());
        return message;
    }
}
