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

import java.util.ArrayList;
import java.util.Map;

public class InteractElytraListener implements Listener {

    final Map<String, Integer> map;
    final private int limit;
    final private FileConfiguration config;
    final ArrayList<String> worldsName;

    public InteractElytraListener(Map<String, Integer> map, int limit, FileConfiguration config){
        this.map = map;
        this.limit = limit;
        this.config = config;
        this.worldsName = (ArrayList<String>) this.config.getStringList("worlds-to-check");
    }

    // Add one to the elytra harvest when clicking on elytra and not limit is reach
    @EventHandler (priority = EventPriority.HIGH)
    public void onInteractElytra(EntityDamageByEntityEvent event){

        if(event.isCancelled()) return;
        if(! (event.getEntity() instanceof ItemFrame) || ! (event.getDamager() instanceof Player) ) return;

        final Player p = (Player) event.getDamager();
        final String uuid = p.getUniqueId().toString();
        final ItemFrame frame = (ItemFrame) event.getEntity();

        if (frame == null) return;
        if(! frame.getItem().getType().equals(Material.ELYTRA)) return;
        if(! this.worldsName.contains(p.getWorld().getName())) return;

        int playerActualLimit = map.containsKey(uuid) ? map.get(uuid) : 0;

        if(playerActualLimit >= limit){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("limit-reach")));
            event.setCancelled(true);
        }
        else
            map.put(uuid, playerActualLimit+1);


    }

}
