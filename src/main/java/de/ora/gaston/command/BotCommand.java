package de.ora.gaston.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.List;

public abstract class BotCommand {
    private CommandMeta cmd;
    protected final static Color embedColor = new Color(255, 206, 117);

    private BotCommand() {
    }

    protected BotCommand(final CommandMeta cmd) {
        this.cmd = cmd;
    }

    public CommandMeta getCmd() {
        return cmd;
    }

    public TextChannel getChannel(final Guild guild, final String channelName) {
        final List<TextChannel> matchingChannels = guild.getTextChannelsByName(channelName, true);
        if (matchingChannels == null || matchingChannels.isEmpty()) {
            return null;
        }
        return matchingChannels.get(0);
    }

    public abstract void perform(final MessageReceivedEvent event);


    protected MessageEmbed embed(String title, String msg) {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        if (StringUtils.isNotBlank(title)) {
            embedBuilder.setTitle(title);
        }
        return embedBuilder.setColor(embedColor).setDescription(msg).build();
    }
}
