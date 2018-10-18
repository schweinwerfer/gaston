package de.ora.gaston.command;

import de.ora.gaston.util.Config;
import de.ora.gaston.util.Emoji;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
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

        StringBuilder sb = new StringBuilder();
        sb.append("Ich bin ")
                .append(String.format(VERSION_STRING_SHORT_TEMPLATE, event.getJDA().getSelfUser().getName(), Config.getVersion())).append(System.lineSeparator())
                .append("Viel zu lernen es gibt!");

        final MessageEmbed messageEmbed = new EmbedBuilder().setTitle("Version").setColor(embedColor).setDescription(sb.toString()).build();

        channel.sendMessage(messageEmbed).queue();
    }
}
