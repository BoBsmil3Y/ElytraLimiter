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


    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        if(this.config.contains("data")) this.retrieveData();

        this.getCommand("esetlimit").setExecutor(new ElimitCommand(this));
        this.getCommand("eamount").setExecutor(new AmountCommand(this.playersLimit, config));
        getServer().getPluginManager().registerEvents(new InteractElytraListener(this.playersLimit, config), this);
        getServer().getPluginManager().registerEvents(new LoosingElytraListener(this.playersLimit), this);

    }

    @Override
    public void onDisable() {

        if(! playersLimit.isEmpty())
            this.saveToData();

    }

    /**
     * Save the HashMap of limit per player to the config.
     */
    private void saveToData() {

        Iterator<Map.Entry<String, Integer>> itr = playersLimit.entrySet().iterator();
        Map.Entry<String, Integer> entry;

        while(itr.hasNext()){
             entry = itr.next();
             this.config.set("data." + entry.getKey(), entry.getValue());
        }

        this.saveConfig();
    }

    /**
     * Retrieve back the HashMap from the config file, and clear the section.
     */
    private void retrieveData() {

        this.config.getConfigurationSection("data").getKeys(false).forEach(key -> {
            playersLimit.put(key, this.config.getInt("data." + key));
        });
        this.config.set("data", new HashMap<String, Integer>());
        this.saveConfig();

    }

}
