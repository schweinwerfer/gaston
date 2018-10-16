package de.ora.gaston.command;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class WelcomeCmd extends BotCommand {

    private final String introChannelName;

    public WelcomeCmd(final String introChannelName) {
        super(CommandMeta.INTRO);
        this.introChannelName = introChannelName;
    }

    @Override
    public void perform(MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();
        final User author = event.getAuthor();
        final TextChannel introChannel = getChannel(guild, introChannelName);
        String message = welcomeMsg(guild, author, introChannel);
        channel.sendMessage(message).queue();
    }

    public void perform(GuildMemberJoinEvent event) {
        final Guild guild = event.getGuild();
        final TextChannel messageChannel = guild.getDefaultChannel();
        if (messageChannel == null) {
            return;
        }
        final Member member = event.getMember();
        final TextChannel introChannel = getChannel(guild, introChannelName);
        String message = welcomeMsg(guild, member, introChannel);
        messageChannel.sendMessage(message).queue();
    }

    private String welcomeMsg(final Guild guild, final IMentionable member, final TextChannel introChannel) {
        String message = "Hallo %user%! Willkommen auf %guild%. Stell dich doch kurz auf %introchannel% vor";
        message = message.replace("%user%", member.getAsMention());
        message = message.replace("%guild%", guild.getName());
        message = message.replace("%introchannel%", introChannel.getAsMention());
        return message;
    }
}
