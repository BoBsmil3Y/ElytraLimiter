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

    /**
     * Command that will set the global limit of elytra
     * that a player can collect.
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final FileConfiguration config = this.pl.getConfig();

        if(! (sender instanceof ConsoleCommandSender)){
            Player p = (Player) sender;
            if(! p.hasPermission("elytra.setlimit"))
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("no-perm")));
        }

        if(args.length != 1) return false;
        final int limit;

        try { limit = Integer.parseInt(args[0]); }
        catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("not-a-whole-number")));
            return true;
        }

        this.pl.getConfig().set("limit-per-player", limit);
        this.pl.saveConfig();

        return true;
    }
}
