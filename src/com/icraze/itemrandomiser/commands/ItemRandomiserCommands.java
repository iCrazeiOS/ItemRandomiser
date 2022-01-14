package com.icraze.itemrandomiser.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class ItemRandomiserCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("clag")) {
            // get amount of Items
            int amount = 0;
            for (Entity entity : player.getWorld().getEntities()) {
                if (entity instanceof Item) {
                    // remove Item and increment amount
                    entity.remove();
                    amount++;
                }
            }

            // if no Items
            if (amount == 0) {
                player.sendMessage(ChatColor.GOLD + "(!) No ground items were found!");
                return true;
            }

            // Show how many items were removed
            player.sendMessage(ChatColor.GOLD + "Successfully removed " + String.valueOf(amount) + " entities!");
            return true;
        }
        return false;
    }
}
