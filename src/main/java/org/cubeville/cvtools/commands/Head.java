package org.cubeville.cvtools.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.utils.ColorUtils;
import org.cubeville.cvtools.CVTools;

public class Head extends Command {
    public Head() {
        super("head");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterString());
        setPermission("cvtools.head");
    }
    
    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {
        String uuid = (String) baseParameters.get(0);
        String url = (String) baseParameters.get(1);
        String command = "minecraft:give " + player.getDisplayName() + " skull 1 3 {display:{Name:\"Custom Head\"},SkullOwner:{Id:\"" + uuid + "\", Properties:{textures:[{Value:\"" + url + "\"}]}}}";
        System.out.println("Execute command: " + command);
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
        return null;
    }
}
