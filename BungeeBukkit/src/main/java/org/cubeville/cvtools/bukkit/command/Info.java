package org.cubeville.cvtools.bukkit.command;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.cubeville.commons.bukkit.command.Command;
import org.cubeville.commons.bukkit.command.CommandParameterEnumeratedString;
import org.cubeville.commons.bukkit.command.CommandResponse;
import org.cubeville.commons.bukkit.util.BlockUtils;

public class Info extends Command {

    public Info() {
        super("info");
        Set<String> types = new HashSet<>();
        types.add("world");
        types.add("selection");
        addBaseParameter(new CommandParameterEnumeratedString(types));
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
        CommandResponse ret = new CommandResponse();
        String type = (String) baseParameters.get(0);
        if(type.equals("world")) {
            World world = player.getLocation().getWorld();
            ret.addMessage("Current world: " + world.getName() + ", UUID = " + world.getUID());
        }
        else if(type.equals("selection")) {
            int area = BlockUtils.getWESelectionArea(player);
            Location min = BlockUtils.getWESelectionMin(player);
            Location max = BlockUtils.getWESelectionMax(player);
            int width = max.getBlockX() - min.getBlockX() + 1;
            int length = max.getBlockZ() - min.getBlockZ() + 1;
            int height = max.getBlockY() - min.getBlockY() + 1;
            ret.addMessage("Selection: Width=" + width + ", Length=" + length + ", Height=" + height + ", Area=" + area);
        }
        return ret;
    }
}
