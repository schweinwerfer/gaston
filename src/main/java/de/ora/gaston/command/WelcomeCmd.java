package de.ora.gaston.command;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class WelcomeCmd extends BotCommand {

    private final String infoChannelName;
    private final String introChannelName;

    public WelcomeCmd(final String infoChannelName, final String introChannelName) {
        super(CommandMeta.INTRO);
        this.infoChannelName = infoChannelName;
        this.introChannelName = introChannelName;
    }

    @Override
    public void perform(MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();
        final User author = event.getAuthor();
        String message = welcomeMsg(guild, author, infoChannelName, introChannelName);
        channel.sendMessage(message).queue();
    }

    public void perform(GuildMemberJoinEvent event) {
        final Guild guild = event.getGuild();
        final TextChannel messageChannel = guild.getDefaultChannel();
        if (messageChannel == null) {
            return;
        }
        final Member author = event.getMember();
        String message = welcomeMsg(guild, author, infoChannelName, introChannelName);
        messageChannel.sendMessage(message).queue();
    }

    private String welcomeMsg(final Guild guild, final IMentionable member, final String infoChannelName, final String introChannelString) {
        String message = "Hallo %user%, willkommen auf %guild%!%n%Erfahre mehr unter %infochannel% und stelle dich kurz auf %introchannel% vor.";
        message = message.replace("%user%", member.getAsMention());
        message = message.replace("%guild%", guild.getName());

        if (StringUtils.isNotBlank(infoChannelName)) {
            final TextChannel infoChannel = getChannel(guild, infoChannelName);
            if (infoChannel != null) {
                message = message.replace("%infochannel%", infoChannel.getAsMention());
            }
        }
        if (StringUtils.isNotBlank(introChannelString)) {
            final TextChannel introChannel = getChannel(guild, introChannelString);
            if (introChannel != null) {
                message = message.replace("%introchannel%", introChannel.getAsMention());
            }
        }

        message = message.replaceAll("%n%", System.lineSeparator());

        return message;
    }
}
