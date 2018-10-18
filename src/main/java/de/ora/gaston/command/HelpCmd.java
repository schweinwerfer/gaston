package de.ora.gaston.command;

import de.ora.gaston.util.Emoji;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class HelpCmd extends BotCommand {

    public HelpCmd() {
        super(CommandMeta.VERSION);
    }

    @Override
    public void perform(MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();

        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());

        for (CommandMeta meta : CommandMeta.values()) {
            final String cmd = meta.getCmd();
            sb.append(cmd).append(" - ").append(meta.getDescription()).append(System.lineSeparator());
        }
        final MessageEmbed messageEmbed = new EmbedBuilder().setTitle("Mögliche Befehle").setColor(embedColor).setDescription(sb.toString()).build();
        channel.sendMessage(messageEmbed).queue();
    }


}
