package xyz.ryozuki.helperbot;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements CommandExecutor {

    private final List<String> commands;
    private HelperBot plugin;

    CommandHandler() {
        this.plugin = HelperBot.getInstance();

        commands = new ArrayList<>();
        commands.add("setname");
        commands.add("reload");
    }

    public List<String> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§aSubcommands: " + String.join(", ", commands));
            return true;
        }

        String subCmd = args[0];
        args = (String[]) ArrayUtils.remove(args, 0);

        String noPermissionText = "§4You don't have permission to execute this command.§r";

        if (subCmd.equalsIgnoreCase("setname")) {
            if (!sender.hasPermission("helperbot.setname")) {
                sender.sendMessage(noPermissionText);
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage("§cMissing argument <name>");
                return true;
            }
            plugin.setBotName(String.join(" ", args));
            sender.sendMessage(
                    String.format(
                            "§aBot name succesfully set to:§r '%s§r'",
                            ChatColor.translateAlternateColorCodes('&',
                                    plugin.getBotName())
                    )
            );
        } else if (subCmd.equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("helperbot.reload")) {
                sender.sendMessage(noPermissionText);
                return true;
            }
            plugin.reloadConfig();
            plugin.reloadQuestions();
            sender.sendMessage("§aSuccesfully reloaded.§r");
        } else {
            sender.sendMessage("§cUnknown command. Available commands: " + String.join(", ", commands));
        }

        return true;
    }
}

