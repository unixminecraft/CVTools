package org.cubeville.cvtools.bungeebukkit.command;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cubeville.commons.bungeebukkit.command.BaseCommand;
import org.cubeville.commons.bungeebukkit.command.CommandExecutionException;
import org.cubeville.commons.bungeebukkit.command.CommandParameterOnlinePlayer;
import org.cubeville.commons.bungeebukkit.command.CommandParameterString;
import org.cubeville.commons.bungeebukkit.command.CommandResponse;
import org.cubeville.commons.bungeebukkit.util.ColorUtils;

public class Head extends BaseCommand {
    public Head() {
        super("head");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterString());
        addParameter("name", true, new CommandParameterString());
        addParameter("player", true, new CommandParameterOnlinePlayer());
        setPermission("cvtools.head");
    }
    
    @Override
    public CommandResponse execute(CommandSender sender, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {
        String uuid = (String) baseParameters.get(0);
        String url = (String) baseParameters.get(1);
        String name = (String) parameters.get("name");
        if(name == null) name = "Custom Head";
        Player player;
        if(parameters.containsKey("player")) {
            player = (Player) parameters.get("player");
        }
        else if(sender instanceof Player) {
            player = (Player) sender;
        }
        else {
            throw new CommandExecutionException("Player parameter must be set if using on console.");
        }
        String command = "minecraft:give " + player.getName() + " skull 1 3 {display:{Name:\"" + ColorUtils.addColor(name) + "\"},SkullOwner:{Id:\"" + uuid + "\", Properties:{textures:[{Value:\"" + url + "\"}]}}}";
        System.out.println("Execute command: " + command);
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
        return null;
    }
}
