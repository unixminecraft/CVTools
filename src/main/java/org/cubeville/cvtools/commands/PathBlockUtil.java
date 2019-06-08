package org.cubeville.cvtools.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.utils.BlockUtils;

public class PathBlockUtil extends Command
{

    public PathBlockUtil() {
        super("pathblockutil");
        // TODO: Add flags to enable other y levels than 0
        // TODO: Add material selection (from/to)
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        Location min = BlockUtils.getWESelectionMin(player);
        Location max = BlockUtils.getWESelectionMax(player);

        if(min == null) throw new CommandExecutionException("No WE selection found.");

        World world = min.getWorld();
        
        int targetY = 0;
        Material targetMaterial = Material.DIAMOND_BLOCK;
        Material sourceMaterial = Material.DIAMOND_BLOCK;
        
        for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    if(world.getBlockAt(x, y, z).getType() == sourceMaterial) {
                        Block block = world.getBlockAt(x, targetY, z);
                        if(block.getType() != targetMaterial) block.setType(targetMaterial);
                    }
                }
            }
        }

        return null;
    }
}
