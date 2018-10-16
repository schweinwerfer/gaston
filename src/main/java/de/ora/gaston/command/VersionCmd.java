package de.ora.gaston.command;

import de.ora.gaston.util.Config;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class VersionCmd extends BotCommand {
    private static final String VERSION_STRING_SHORT_TEMPLATE = "%s V%s";

    public VersionCmd() {
        super(CommandMeta.VERSION);
    }

    @Override
    public void perform(MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final User author = event.getAuthor();

        StringBuilder sb = new StringBuilder("Hallo ");
        sb.append(author.getName())
                .append("!")
                .append(System.lineSeparator())
                .append("Ich bin ")
                .append(String.format(VERSION_STRING_SHORT_TEMPLATE, event.getJDA().getSelfUser().getName(), Config.getVersion()));

        channel.sendMessage(sb.toString()).queue();
    }
}
