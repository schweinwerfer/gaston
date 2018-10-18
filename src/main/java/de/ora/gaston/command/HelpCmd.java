package de.ora.gaston.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

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

        channel.sendMessage(embed("Mögliche Befehle", sb.toString())).queue();
    }

}
