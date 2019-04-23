package org.cubeville.cvtools.bukkit.command;

//       add search options for groups of entities

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.cubeville.commons.bukkit.command.Command;
import org.cubeville.commons.bukkit.command.CommandExecutionException;
import org.cubeville.commons.bukkit.command.CommandParameterInteger;
import org.cubeville.commons.bukkit.command.CommandParameterString;
import org.cubeville.commons.bukkit.command.CommandResponse;

public class CheckEntities extends Command {

    private int defaultSize;
    private int defaultMinCount;
    
    public CheckEntities() {
        super("checkentities");
        addOptionalBaseParameter(new CommandParameterString());
        addParameter("r", true, new CommandParameterInteger());
        addParameter("min", true, new CommandParameterInteger());
        defaultSize = 50;
        defaultMinCount = 100;
        setPermission("cvtools.checkentities");
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {
        CommandResponse ret = new CommandResponse();
        World world = player.getWorld();

        int size = defaultSize;
        if(parameters.get("r") != null) {
            size = (int) parameters.get("r") * 2;
            if(size < 4 || (size > 60 && !player.hasPermission("cvtools.unlimited"))) throw new CommandExecutionException("Size must be in [2;30].");
        }

        int minCount = defaultMinCount;
        if(parameters.get("min") != null) {
            if(baseParameters.size() == 1) throw new CommandExecutionException("Minimum does work for search with a specific player.");
            minCount = (int) parameters.get("min");
            if(minCount < 50) throw new CommandExecutionException("Minimum must be bigger than 50.");
        }

        if(baseParameters.size() == 0) {
            int pcnt = 0;
            List<Player> players = world.getPlayers();
            for(Player cplayer: players) {
                Collection<Entity> entities = world.getNearbyEntities(cplayer.getLocation(), size, 256, size);
                int cnt = 0;
                for(Entity entity: entities) {
                    if(isOfEntityClass(entity)) cnt++;
                }
                if(cnt >= minCount) {
                    ret.addMessage("&aPlayer &9" + cplayer.getName() + "&a: &9" + cnt + "&a entities at &9" + cplayer.getLocation().getBlockX() + "/" + cplayer.getLocation().getBlockZ() + "&a.");
                    pcnt++;
                    if(pcnt++ >= 10) {
                        ret.addMessage("&c... and more.");
                    }
                }
            }
            if(ret.getMessages() == null) {
                ret.setBaseMessage("&cNo players near too many animals found.");
            }
            else {
                ret.setBaseMessage("&6--- Searching for players with more than " + minCount + " nearby entities.");
            }
        }

        else {
            String playerName = (String) baseParameters.get(0);
            Player cplayer = Bukkit.getPlayer(playerName);
            if(cplayer == null) throw new CommandExecutionException("Player " + playerName + " not found!");
            if(!cplayer.isOnline()) throw new CommandExecutionException("Player " + playerName + " is not online!");
            Collection<Entity> entities = cplayer.getWorld().getNearbyEntities(cplayer.getLocation(), size, 256, size);
            Map<String, Integer> count = new HashMap<>();
            int cnt = 0;
            for(Entity entity: entities) {
                if(isOfEntityClass(entity)) {
                    String entityName = entity.getClass().getSimpleName();
                    if(entityName.startsWith("Craft")) entityName = entityName.substring(5);
                    if(!count.containsKey(entityName)) count.put(entityName, 0);
                    count.put(entityName, count.get(entityName) + 1);
                    cnt++;
                }
            }
            if(count.size() > 0) {
                ret.addMessage("&6--- Counting entities near &9" + playerName + "&6. Search region size: " + size + " ---");
                List<String> sortedEntities = new ArrayList<String>(count.keySet());
                Collections.sort(sortedEntities);
                for(String entity: sortedEntities) {
                    ret.addMessage("&aNumber of &9" + entity + "&a entities: &9" + count.get(entity));
                }
                ret.addMessage("&aTotal: &9" + cnt);
            }
            else {
                ret.setBaseMessage("&cNo entites found near specified player.");
            }
        }

        return ret;
    }

    private boolean isOfEntityClass(Entity entity) {
        EntityType type = entity.getType();
        if(type == EntityType.SHEEP || type == EntityType.CHICKEN || type == EntityType.COW || type == EntityType.RABBIT || type == EntityType.PIG || type == EntityType.MUSHROOM_COW ||
           type == EntityType.VILLAGER || type == EntityType.HORSE || type == EntityType.WOLF || type == EntityType.OCELOT || type == EntityType.IRON_GOLEM ||
           type == EntityType.SNOWMAN || type == EntityType.PIG_ZOMBIE || type == EntityType.ZOMBIE || type == EntityType.SKELETON || type == EntityType.SPIDER ||
           type == EntityType.CAVE_SPIDER || type == EntityType.SKELETON_HORSE || type == EntityType.GUARDIAN) {
            return true;
        }
        return false;
    }
        
}
