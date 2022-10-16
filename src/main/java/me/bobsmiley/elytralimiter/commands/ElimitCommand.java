package me.bobsmiley.elytralimiter.commands;

import me.bobsmiley.elytralimiter.ElytraLimiter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ElimitCommand implements CommandExecutor {

    final private ElytraLimiter pl;

    public ElimitCommand(ElytraLimiter pl){ this.pl = pl; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = this.pl.getConfig();

        if(! (sender instanceof ConsoleCommandSender)){
            Player p = (Player) sender;
            if(! p.hasPermission("elytralimiter.admin"))
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("no-perm")));
        }

        if(args.length != 1) return false;
        int limit = 0;

        try { limit = Integer.parseInt(args[0]); }
        catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("not-a-whole-number")));
        }

        config.set("limit-per-player", limit);
        this.pl.saveConfig();

        return true;
    }
}
