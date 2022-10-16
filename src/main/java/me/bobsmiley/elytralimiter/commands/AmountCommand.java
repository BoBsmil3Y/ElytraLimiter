package me.bobsmiley.elytralimiter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;

public class AmountCommand implements CommandExecutor {

    final Map<String, Integer> map;
    final private int limit;
    final private FileConfiguration config;

    public AmountCommand(Map<String, Integer> map, int limit, FileConfiguration config){
        this.map = map;
        this.limit = limit;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof ConsoleCommandSender)
            sender.sendMessage("Only executable by players.");

        final Player p = (Player) sender;
        final String uuid = p.getUniqueId().toString();

        if(! map.containsKey(uuid)) {
            map.put(uuid, 0);
            p.sendMessage("Not key found. Settings to 0");
        } else
            p.sendMessage("Amount of limit : " + map.get(uuid));

        return true;
    }
}
