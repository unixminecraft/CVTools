package org.cubeville.cvtools.bukkit.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cubeville.commons.bukkit.command.Command;
import org.cubeville.commons.bukkit.command.CommandExecutionException;
import org.cubeville.commons.bukkit.command.CommandResponse;

public class Itemname extends Command {

    Map<UUID, String> itemnames = new HashMap<>();
    
    public Itemname() {
        super("itemname");
        addFlag("copy");
        addFlag("paste");
        setPermission("cvtools.itemname");
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        UUID uuid = player.getUniqueId();
        if(flags.size() == 0 || flags.size() == 2) throw new CommandExecutionException("One of 'copy' and 'paste' flags need to be given!");

        ItemStack item = player.getInventory().getItemInMainHand();
        if(item == null || item.getType() == Material.AIR) throw new CommandExecutionException("You must hold an item in your hand!");

        ItemMeta meta = item.getItemMeta();
        
        if(flags.contains("copy")) {
            itemnames.put(uuid, meta.getDisplayName());
        }
        else {
            if(!itemnames.containsKey(uuid)) throw new CommandExecutionException("You have to copy an item name first!");
            meta.setDisplayName(itemnames.get(uuid));
            item.setItemMeta(meta);
        }

        return null;
    }

}
