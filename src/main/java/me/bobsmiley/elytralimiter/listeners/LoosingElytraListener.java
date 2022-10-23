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

    public LoosingElytraListener(Map<String, Integer> map){
        this.map = map;
    }

    /**
     * On player death, if it's caused by the void, it will decrease the amount
     * of elytra that was in the player inventory from the player actual limit.
     * @param event the player death event
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        final Player p = event.getEntity();

        if(! p.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.VOID)) return;

        int elytraAmount = 0;
        ItemStack[] items = p.getInventory().getContents();

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


