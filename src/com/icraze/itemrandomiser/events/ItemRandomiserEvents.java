package com.icraze.itemrandomiser.events;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemRandomiserEvents implements Listener {
    public static HashMap<Material, Material> knownBlocks = new HashMap<>();
    static Random rnd = new Random();

    public static Material getRandomMaterial(BlockBreakEvent event) {
        // get a random Material
        Material mat = Material.values()[rnd.nextInt(Material.values().length)];

        // hacky way to test if the Material can be dropped
        try {
            // drop the item far from the player
            Item testItem = event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation().add(30, 0, 30), new ItemStack(mat, 1));
            // immediately remove item
            testItem.remove();
        } catch (Exception e) { // error has been caught - the item can not be dropped
            return getRandomMaterial(event); // regenerate Material
        }

        return mat;
    }

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        // if block isn't going to drop an item, do nothing
        if (event.getBlock().getDrops().isEmpty()) return;

        // get broken block type
        Material block = event.getBlock().getType();
        // if type isn't in hashmap, set its value to a random Material
        if (!knownBlocks.containsKey(block)) knownBlocks.put(block, getRandomMaterial(event));

        // cancel event (stops original drop)
        event.setCancelled(true);
        // delete the block manually (because we just cancelled the event)
        event.getPlayer().getWorld().getBlockAt(event.getBlock().getLocation()).setType(Material.AIR);

        // dropping an ItemStack of 100+(?) items causes them to be invisible
        // we loop and drop ItemStacks of 1, in order to fix this issue
        for (int i = 0; i <= rnd.nextInt(1000 - 1 + 1) + 1; i++) event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(knownBlocks.get(block), 1));
    }
}
