package org.cubeville.cvtools;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
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
        commandParser.addCommand(new Itemname());
        commandParser.addCommand(new KillEntities());

        noGrowthWorlds = new HashSet<>();
        noGrowthWorlds.add(UUID.fromString("f2d1566c-af98-4f1c-beb8-793a17deaf37"));

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("cvtools")) {
            return commandParser.execute(sender, args);
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
        return false;
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event) {
        if(noGrowthWorlds.contains(event.getBlock().getLocation().getWorld().getUID())) {
            event.setCancelled(true);
        }
    }

}
