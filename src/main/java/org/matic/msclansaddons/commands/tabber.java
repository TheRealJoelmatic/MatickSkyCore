package org.matic.msclansaddons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class tabber implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!(sender instanceof Player)) {
            return completions; // Return an empty list, not null
        }

        Player player = ((Player) sender).getPlayer();

        if (!cmd.getName().equalsIgnoreCase("clan")) {
            return completions; // Return an empty list, not null
        }

        if (args.length != 1) {
            return completions; // Return an empty list, not null
        }

        // Tab complete the subcommands for /clan
        completions.add("create");
        completions.add("invite");
        completions.add("accept");
        completions.add("kick");
        completions.add("stats");
        completions.add("base");
        completions.add("setbase");
        completions.add("leave");
        completions.add("delete");
        completions.add("list");

        return completions;
    }
}
