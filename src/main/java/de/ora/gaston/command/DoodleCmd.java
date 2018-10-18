package de.ora.gaston.command;

import de.ora.gaston.util.Emoji;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class DoodleCmd extends BotCommand {

    private final String anouncementChannelName;

    public DoodleCmd(final String anouncementChannelName) {
        super(CommandMeta.DOODLE);
        this.anouncementChannelName = anouncementChannelName;
    }

    @Override
    public void perform(MessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        final Guild guild = event.getGuild();

        if (StringUtils.isNotBlank(anouncementChannelName)) {
            final TextChannel infoChannel = getChannel(guild, anouncementChannelName);
            if (infoChannel != null) {
                final String text = doodleMsg(guild, event.getMessage().getContentRaw());
                if (text != null) {
                    infoChannel.sendMessage(Emoji.MEGAPHONE + " " + text).queue();
                } else {
                    channel.sendMessage(embed("Fehler", "Das habe ich leider nicht verstanden. Fehlt die Doodle URL?")).queue();
                }
            } else {
                channel.sendMessage(embed("Fehler", "Kann leider nicht auf Kanal posten. Existiert Kanal '" + anouncementChannelName + "' überhaupt?")).queue();
            }

            return;
        }

        channel.sendMessage(embed("Fehler", "Hat leider nicht geklappt")).queue();
    }


    private String doodleMsg(final Guild guild, final String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        final String[] split = content.split(" ");
        if (split.length != 2 || !split[1].startsWith("http")) {
            return null;
        }

        String message = "@everyone Es ist wieder soweit!%n%Eine neue Runde wurde gestartet, um einen Termin für das nächste Treffen zu finden.%n%Interessierte stimmen bitte unter %doodleurl% ab.";
        message = message.replace("%doodleurl%", split[1].trim());
        message = message.replaceAll("%n%", System.lineSeparator());

        return message;
    }
}
