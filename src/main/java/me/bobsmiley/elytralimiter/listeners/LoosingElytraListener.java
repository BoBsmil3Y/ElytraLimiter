package me.bobsmiley.elytralimiter.listeners;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class LoosingElytraListener implements Listener {

    final Map<String, Integer> map;
    final private int limit;
    final private FileConfiguration config;

    public LoosingElytraListener(Map<String, Integer> map, int limit, FileConfiguration config){
        this.map = map;
        this.limit = limit;
        this.config = config;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        Player p = event.getEntity();

        if(! p.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.VOID)) return;

        ItemStack[] items = p.getInventory().getContents();
        int elytraAmount = 0;

        for(ItemStack item: items)
            if(item != null && item.getType().equals(Material.ELYTRA))
                elytraAmount++;

        final String uuid = p.getUniqueId().toString();

        if(! map.containsKey(uuid)) {
            map.put(uuid, 0);
            return;
        }

        int playerActualLimit = map.get(uuid);

        if (playerActualLimit != 0){
            playerActualLimit = playerActualLimit - elytraAmount < 0 ? 0 : playerActualLimit - elytraAmount;
            map.put(uuid, playerActualLimit);
        }

    }
}


