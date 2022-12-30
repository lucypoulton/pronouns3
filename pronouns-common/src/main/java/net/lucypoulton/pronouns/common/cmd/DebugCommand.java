package net.lucypoulton.pronouns.common.cmd;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.Hidden;
import net.kyori.adventure.text.Component;
import net.lucypoulton.pronouns.api.ProNounsPlugin;
import net.lucypoulton.pronouns.common.platform.CommandSender;
import net.lucypoulton.pronouns.common.platform.Platform;

public class DebugCommand {

    private static final String DEBUG_FORMAT = """
            ProNouns v%s (%s, %s)
            Store %s
            Config %s
            %s predefined sets""";

    private final ProNounsPlugin plugin;
    private final Platform platform;

    public DebugCommand(ProNounsPlugin plugin, Platform platform) {
        this.plugin = plugin;
        this.platform = platform;
    }

    private static String shortenedClassName(Class<?> clazz) {
        final var split = clazz.getName().split("\\.");
        if (split.length <= 3) return clazz.getName();
        final var out = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            out[i] = i >= 3 ? split[i] : split[i].substring(0, 1);
        }
        return String.join(".", out);
    }

    @Hidden
    @CommandMethod("pronouns debug")
    public void execute(CommandSender sender) {
        sender.sendMessage(Component.text(
                String.format(DEBUG_FORMAT, platform.currentVersion(), platform.name(), platform.config().updateChannel(),
                        shortenedClassName(plugin.store().getClass()),
                        shortenedClassName(platform.config().getClass()),
                        plugin.store().predefined().get().size()
                )
        ));
    }
}
