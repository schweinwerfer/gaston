package de.ora.gaston.command;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCmd extends BotCommand {

    public HelpCmd() {
        super(CommandMeta.VERSION);
    }

    @Override
    public void perform(MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();

        StringBuilder sb = new StringBuilder();
        sb.append("Mögliche Befehle:").append(System.lineSeparator());
        for (CommandMeta meta : CommandMeta.values()) {
            sb.append(meta.getCmd()).append(" - ").append(meta.getDescription()).append(System.lineSeparator());
        }

        channel.sendMessage(sb.toString()).queue();
    }
}
