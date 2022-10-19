package me.bobsmiley.elytralimiter;

import me.bobsmiley.elytralimiter.commands.AmountCommand;
import me.bobsmiley.elytralimiter.commands.ElimitCommand;
import me.bobsmiley.elytralimiter.listeners.InteractElytraListener;
import me.bobsmiley.elytralimiter.listeners.LoosingElytraListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public final class ElytraLimiter extends JavaPlugin {

    final private FileConfiguration config = this.getConfig();
    private Map<String, Integer> playersLimit = new HashMap<>();
    private int limit = this.config.getInt("limit-per-player");

    /* Known problem :
     * If player give to each other Elytras,they will not be able to take new one.
     * It can be complicated when they are playing in groups and the player
     * */
    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        if(this.config.contains("data")) this.retrieveData();

        this.getCommand("esetlimit").setExecutor(new ElimitCommand(this));
        this.getCommand("eamount").setExecutor(new AmountCommand(this.playersLimit, limit, config));
        getServer().getPluginManager().registerEvents(new InteractElytraListener(this.playersLimit, limit, config), this);
        getServer().getPluginManager().registerEvents(new LoosingElytraListener(this.playersLimit, config), this);

    }

    @Override
    public void onDisable() {

        if(! playersLimit.isEmpty())
            this.saveToData();

    }

    private void saveToData() {

        Iterator<Map.Entry<String, Integer>> itr = playersLimit.entrySet().iterator();
        Map.Entry<String, Integer> entry;

        while(itr.hasNext()){
             entry = itr.next();
             this.config.set("data." + entry.getKey(), entry.getValue());
        }

        this.saveConfig();
    }

    private void retrieveData() {

        this.config.getConfigurationSection("data").getKeys(false).forEach(key -> {
            playersLimit.put(key, this.config.getInt("data." + key));
        });
        this.config.set("data", new HashMap<String, Integer>());
        this.saveConfig();

    }

}
