package de.ora.gaston.command;

import java.util.HashMap;
import java.util.Map;

public enum CommandMeta {
    VERSION("!version", "Zeigt die aktuelle Version an"),
    HELP("!help", "Zeigt alle möglichen Befehle an"),
    INTRO("!intro", "Zeigt den standard Willkommenstext an");

    private final String cmd;
    private final String description;
    private final static Map<String, CommandMeta> lookup;

    static {
        lookup = new HashMap<>();
        for (CommandMeta meta : CommandMeta.values()) {
            lookup.put(meta.cmd, meta);
        }
    }

    CommandMeta(String cmd, String description) {
        this.cmd = cmd;
        this.description = description;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDescription() {
        return description;
    }

    public static CommandMeta lookup(final String cmd) {
        return lookup.get(cmd);
    }
}
