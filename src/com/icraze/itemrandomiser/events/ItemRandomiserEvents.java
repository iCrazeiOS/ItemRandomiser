package com.icraze.itemrandomiser.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Random;

public class ItemRandomiserEvents implements Listener {
    public static HashMap<Material, Material> knownBlocks = new HashMap<>();

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getDrops().isEmpty()) return;
        Material block = event.getBlock().getType();
        Random rnd = new Random();
        if (!knownBlocks.containsKey(block)) knownBlocks.put(block, Material.values()[rnd.nextInt(Material.values().length)]);
        event.setCancelled(true);
        event.getPlayer().getWorld().getBlockAt(event.getBlock().getLocation()).setType(Material.AIR);
        Integer randint = rnd.nextInt(1000 - 1 + 1) + 1;
        for (int a = 0; a <= randint; a++) event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(knownBlocks.get(block), 1));
    }
}