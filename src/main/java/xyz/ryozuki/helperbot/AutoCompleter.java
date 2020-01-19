package xyz.ryozuki.helperbot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        CommandHandler commandHandler = HelperBot.getInstance().getCommandHandler();

        List<String> autoCompleteList = new ArrayList<>();

        if (args.length == 1) {
            String subCommand = args[0];
            for (String cmd : commandHandler.getCommands()) {
                if (cmd.startsWith(subCommand))
                    autoCompleteList.add(cmd);
            }
        }

        return autoCompleteList.isEmpty() ? null : autoCompleteList;
    }
}
