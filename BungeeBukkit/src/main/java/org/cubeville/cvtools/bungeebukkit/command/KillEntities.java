package org.cubeville.cvtools.bungeebukkit.command;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import org.cubeville.commons.bungeebukkit.command.BaseCommand;
import org.cubeville.commons.bungeebukkit.command.CommandExecutionException;
import org.cubeville.commons.bungeebukkit.command.CommandParameterEnum;
import org.cubeville.commons.bungeebukkit.command.CommandParameterInteger;
import org.cubeville.commons.bungeebukkit.command.CommandParameterString;
import org.cubeville.commons.bungeebukkit.command.CommandParameterWorld;
import org.cubeville.commons.bungeebukkit.command.CommandResponse;
import org.cubeville.commons.bungeebukkit.util.BlockUtils;

public class KillEntities extends BaseCommand {
    
    public KillEntities() {
        super("killentities");
        addParameter("wg", false, new CommandParameterString());
        addFlag("nopassenger");
        addParameter("world", true, new CommandParameterWorld());
        addParameter("type", false, new CommandParameterEnum(EntityType.class));
        addParameter("noplayer", true, new CommandParameterInteger());
        setPermission("cvtools.killentities");
        setSilentConsole();
    }

    @Override
    public CommandResponse execute(CommandSender sender, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        World world = null;
        if(parameters.containsKey("world")) {
            world = (World)parameters.get("world");
        }
        else if(sender instanceof Player) {
            world = ((Player)sender).getLocation().getWorld();
        }
        else throw new CommandExecutionException("World parameter is mandatory on console.");

        boolean noPlayerRadiusSet = parameters.containsKey("noplayer");
        int noPlayerRadius = 0;
        if(noPlayerRadiusSet) noPlayerRadius = (Integer) parameters.get("noplayer");

        boolean noPassenger = flags.contains("nopassenger");

        ProtectedRegion region = BlockUtils.getWGRegion(world, (String) parameters.get("wg"));
        
        int ret = 0;
        
        List<Entity> entities = world.getEntities();
        for(Entity entity: entities) {
            if(entity.getType() != parameters.get("type")) continue;
            
            if(!region.contains(BukkitUtil.toVector(entity.getLocation()))) continue;
            if(noPassenger == true && entity.getPassengers().size() != 0) continue;
            if(noPlayerRadiusSet) {
                List<Player> players = world.getPlayers();
                boolean playerInRadius = false;
                for(Player p: players) {
                    if(p.getLocation().distance(entity.getLocation()) < noPlayerRadius) {
                        playerInRadius = true;
                        break;
                    }
                }
                if(playerInRadius) continue;
            }
            entity.remove();
            ret++;
        }

        return new CommandResponse("&a" + (ret > 0 ? ret : "No") + " entities removed.");
    }

}
