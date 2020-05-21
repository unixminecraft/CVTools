package org.cubeville.cvtools.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandParameterWorld;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.commons.utils.BlockUtils;

public class CheckRegionPlayers extends BaseCommand {

    public CheckRegionPlayers() {
        super("checkregionplayers");
        addBaseParameter(new CommandParameterString());
        addParameter("ontrue", true, new CommandParameterString());
        addParameter("onfalse", true, new CommandParameterString());
        addParameter("world", true, new CommandParameterWorld());
    }

    @Override
    public CommandResponse execute(CommandSender sender, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        CommandResponse ret = new CommandResponse();

        World world;
        if(parameters.containsKey("world")) {
            world = (World) parameters.get("world");
        }
        else if(sender instanceof Player) {
            world = ((Player) sender).getLocation().getWorld();
        }
        else {
            throw new CommandExecutionException("World must be specified if used on console.");
        }

        String name = (String) baseParameters.get(0);
        ProtectedRegion region = BlockUtils.getWGRegion(world, name);
        List<Player> players = world.getPlayers();
        String playerList = "";
        int playerCount = 0;
        for(Player p: players) {
            Location loc = p.getLocation();
            if(region.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
                if(playerCount > 0) playerList += ", &e";
                playerList += p.getName();
                playerCount++;
            }
        }

        if(playerCount == 0) {
            if(parameters.containsKey("onfalse")) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (String) parameters.get("onfalse"));
            ret.addMessage("&dNo players found in region &a" + name + "&d.");
        }
        else {
            if(parameters.containsKey("ontrue")) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (String) parameters.get("ontrue"));
            ret.setBaseMessage(playerCount + " players in region &a" + name + "&e: " + playerList);
        }
        return ret;
    }

}
