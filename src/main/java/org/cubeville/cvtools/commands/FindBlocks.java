package org.cubeville.cvtools.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.block.Sign;

import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterEnum;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.commons.utils.BlockUtils;
import org.cubeville.commons.utils.ColorUtils;

public class FindBlocks extends Command {

    public FindBlocks() {
        super("findblocks");
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterEnum(Material.class));
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        Material material = (Material) baseParameters.get(1);
        List<Block> blocks = BlockUtils.getBlocksInWESelectionByType(player, 20000000, material);

        System.out.println("Scanning for blocks: " + (String) baseParameters.get(0));
        for(Block block: blocks) {
            int x = block.getLocation().getBlockX();
            int y = block.getLocation().getBlockY();
            int z = block.getLocation().getBlockZ();
            String desc = block.getWorld().getName() + ";" + x + "/" + y + "/" + z;
            if(material == Material.WALL_SIGN || material == Material.SIGN_POST) {
                Sign sign = (Sign)block.getState();
                String[] lines = sign.getLines();
                String lineCon = "";
                for (String line: lines) {
                    if(lineCon.length() > 0) lineCon += " ";
                    lineCon += ColorUtils.removeColor(line);
                }
                desc += " (" + lineCon + ")";
            }
            System.out.println(desc);
        }
        
        return new CommandResponse("Number of blocks found: " + blocks.size());
    }

}
