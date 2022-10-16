package me.bobsmiley.elytralimiter.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InteractElytraListener implements Listener {

    final Map<String, Integer> map;
    final private int limit;
    final private FileConfiguration config;

    public InteractElytraListener(Map<String, Integer> map, int limit, FileConfiguration config){
        this.map = map;
        this.limit = limit;
        this.config = config;
    }

    // Add one to the elytra harvest when clicking on elytra and not limit is reach
    @EventHandler (priority = EventPriority.HIGH)
    public void onInteractElytra(EntityDamageByEntityEvent event){

        if(event.isCancelled()) return;
        if(! (event.getEntity() instanceof ItemFrame) || ! (event.getDamager() instanceof Player) ) return;

        final Player p = (Player) event.getDamager();
        final String uuid = p.getUniqueId().toString();
        final ItemFrame frame = (ItemFrame) event.getEntity();

        //Ajouter dans config option liste monde
        //if(! p.getWorld().getName().contains("end")) return;

        if (frame != null) {
            ItemStack item = frame.getItem();
            if(item.getType().equals(Material.ELYTRA)){

                int playerActualLimit;

                if(! map.containsKey(uuid)) playerActualLimit = 0;
                else playerActualLimit = map.get(uuid);

                if(playerActualLimit >= limit){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("limit-reach")));
                    event.setCancelled(true);
                }
                else
                    map.put(uuid, playerActualLimit+1);

            }
        }
    }

}
