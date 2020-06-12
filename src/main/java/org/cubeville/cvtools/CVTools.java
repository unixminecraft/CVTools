package org.cubeville.cvtools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import org.cubeville.commons.commands.CommandParser;

import org.cubeville.cvtools.commands.*;

public class CVTools extends JavaPlugin implements Listener {

    private CommandParser cvtoolsParser;
    private CommandParser clearParser;
    private CommandParser moreParser;
    private CommandParser itemParser;
    private CommandParser timeParser;
    private CommandParser weatherParser;

    public void onEnable() {
        cvtoolsParser = new CommandParser();
        cvtoolsParser.addCommand(new ChatColor());
        cvtoolsParser.addCommand(new CheckEntities());
        cvtoolsParser.addCommand(new CheckRegionPlayers());
        cvtoolsParser.addCommand(new CheckSign());
        cvtoolsParser.addCommand(new DelayedTask(this));
        cvtoolsParser.addCommand(new FindBlocks());
        cvtoolsParser.addCommand(new Head());
        cvtoolsParser.addCommand(new Info());
        cvtoolsParser.addCommand(new Itemname());
        cvtoolsParser.addCommand(new KillEntities());
        cvtoolsParser.addCommand(new PathBlockUtil());
        cvtoolsParser.addCommand(new Title());
        
        clearParser = new CommandParser();
        clearParser.addCommand(new Clear());
        
        moreParser = new CommandParser();
        moreParser.addCommand(new More());
        
        itemParser = new CommandParser();
        itemParser.addCommand(new Item());
        
        timeParser = new CommandParser();
        timeParser.addCommand(new Time());
        
        weatherParser = new CommandParser();
        weatherParser.addCommand(new Weather());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("cvtools")) {
            return cvtoolsParser.execute(sender, args);
        }
        else if(command.getName().equals("cvtoolstest")) {
            // Player player = (Player) sender;
            // Location loc = player.getLocation();
            // loc.add(20, 0, 0);
            // FireworkEffect effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.RED).withFade(Color.BLUE).with(FireworkEffect.Type.BALL).build();
            // final Firework fw = player.getLocation().getWorld().spawn(loc, Firework.class);
            // FireworkMeta meta = fw.getFireworkMeta();
            // meta.addEffect(effect);
            // meta.setPower(0);
            // fw.setFireworkMeta(meta);
            // new BukkitRunnable() {
            //     public void run() {
            //         fw.detonate();
            //         //fw.playEffect(EntityEffect.FIREWORK_EXPLODE);
            //         //fw.remove();
            //     }
            // }.runTaskLater(this, 1L);
            Player player = Bukkit.getServer().getPlayer(args[0]);
            sender.sendMessage("Player " + player.getName() + " isdead: " + player.isDead());
        }
        else if(command.getName().equals("ping")) {
        	if(args.length == 0) { sender.sendMessage("§ePong!"); }
        	else { sender.sendMessage("§cSyntax: /ping"); }
        	return true;
        }
        else if(command.getName().equals("shock")) {
        	if(args.length != 1) {
        		sender.sendMessage("§cSyntax: /shock <player>");
        		return true;
        	}
        	Player player = Bukkit.getPlayer(args[0]);
        	if(player == null) {
        		sender.sendMessage("§cPlayer " + args[0] + " not found.");
        		return true;
        	}
        	if(!player.isOnline()) {
        		sender.sendMessage("§cPlayer " + args[0] + " is not online.");
        	}
        	player.getWorld().strikeLightning(player.getLocation());
        	for(Player p : Bukkit.getOnlinePlayers()) { p.sendMessage("§e" + sender.getName() + " shocked " + player.getName()); }
        	return true;
        }
        else if(command.getName().equals("clear")) {
        	return clearParser.execute(sender, args);
        }
        else if(command.getName().equals("more")) {
        	return moreParser.execute(sender, args);
        }
        else if(command.getName().equals("item")) {
        	return itemParser.execute(sender, args);
        }
        else if(command.getName().equals("time")) {
        	return timeParser.execute(sender, args);
        }
        else if(command.getName().equals("weather")) {
        	return weatherParser.execute(sender, args);
        }
        return false;
    }
}
