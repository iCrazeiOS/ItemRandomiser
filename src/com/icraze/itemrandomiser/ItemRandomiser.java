package com.icraze.itemrandomiser;

import com.icraze.itemrandomiser.commands.ItemRandomiserCommands;
import com.icraze.itemrandomiser.events.ItemRandomiserEvents;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemRandomiser extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("clag").setExecutor(new ItemRandomiserCommands());
        getServer().getPluginManager().registerEvents(new ItemRandomiserEvents(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "ItemRandomiser is enabled!");
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "ItemRandomiser is disabled!");
    }
}
