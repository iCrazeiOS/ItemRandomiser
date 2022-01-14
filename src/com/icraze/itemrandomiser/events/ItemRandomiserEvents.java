package com.icraze.itemrandomiser.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Random;

public class ItemRandomiserEvents implements Listener {
    public static HashMap<Material, Material> knownBlocks = new HashMap<>();

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        // if block isn't going to drop an item, do nothing
        if (event.getBlock().getDrops().isEmpty()) return;

        // get block type
        Material block = event.getBlock().getType();
        // if type isn't in hashmap, set its value to a random Material
        Random rnd = new Random();
        if (!knownBlocks.containsKey(block)) knownBlocks.put(block, Material.values()[rnd.nextInt(Material.values().length)]);

        // cancel event (stops original drop)
        event.setCancelled(true);
        // delete the block manually (because we just cancelled the event)
        event.getPlayer().getWorld().getBlockAt(event.getBlock().getLocation()).setType(Material.AIR);

        // dropping an ItemStack of 100+(?) items causes them to be invisible
        // we loop and drop ItemStacks of 1, in order to fix this issue
        Integer randint = rnd.nextInt(1000 - 1 + 1) + 1;
        for (int i = 0; i <= randint; i++) event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(knownBlocks.get(block), 1));
    }
}
