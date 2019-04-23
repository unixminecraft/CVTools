package org.cubeville.cvtools.bukkit.command;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.cubeville.commons.bukkit.command.Command;
import org.cubeville.commons.bukkit.command.CommandExecutionException;
import org.cubeville.commons.bukkit.command.CommandParameterDouble;
import org.cubeville.commons.bukkit.command.CommandParameterString;
import org.cubeville.commons.bukkit.command.CommandResponse;
import org.cubeville.commons.bukkit.util.ColorUtils;
import org.cubeville.cvtools.bukkit.CVTools;

public class DelayedTask extends Command {

    CVTools plugin;
	
    public DelayedTask(CVTools plugin) {
        super("delay");
        addBaseParameter(new CommandParameterDouble());
        addParameter("cmd", true, new CommandParameterString());
        addParameter("chat", true, new CommandParameterString());
        setPermission("cvtools.delay");
        this.plugin = plugin;
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) 
        throws CommandExecutionException {

        int delay = new Double(((double) baseParameters.get(0)) * 20).intValue();
        if(delay <= 0) throw new CommandExecutionException("Delay must be > 0 seconds!");
        
        if (!checkExactlyOne(parameters.containsKey("cmd"), parameters.containsKey("chat")))
            throw new CommandExecutionException("cMust delay a command OR chat!");
                
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    runDelayedTask(player, parameters);
                    player.sendMessage(ColorUtils.addColor("&aAction successfully played!"));
                }
            }, delay);
        return new CommandResponse("&aAction successfully delayed!");
    }
	
    public void runDelayedTask(Player player, Map<String, Object> parameters) {
        if (parameters.containsKey("cmd")) {
            player.performCommand((String) parameters.get("cmd"));
        } else if (parameters.containsKey("chat")) {
            player.chat((String) parameters.get("chat"));
        } else {
            throw new IllegalArgumentException();
        }
    }
}
