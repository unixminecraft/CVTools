package org.cubeville.cvtools;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.cubeville.commons.commands.CommandParser;

import org.cubeville.cvtools.commands.*;

public class CVTools extends JavaPlugin implements Listener {

    private CommandParser commandParser;

    Set<UUID> noGrowthWorlds;

    public void onEnable() {
        commandParser = new CommandParser();
        commandParser.addCommand(new ChatColor());
        commandParser.addCommand(new CheckEntities());
        commandParser.addCommand(new CheckRegionPlayers());
        commandParser.addCommand(new CheckSign());
        commandParser.addCommand(new DelayedTask(this));
        commandParser.addCommand(new Head());
        commandParser.addCommand(new Info());

        noGrowthWorlds = new HashSet<>();
        noGrowthWorlds.add(UUID.fromString("f2d1566c-af98-4f1c-beb8-793a17deaf37"));

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("cvtools")) {
            return commandParser.execute(sender, args);
        }
        return false;
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event) {
        if(noGrowthWorlds.contains(event.getBlock().getLocation().getWorld().getUID())) {
            event.setCancelled(true);
        }
    }

}
