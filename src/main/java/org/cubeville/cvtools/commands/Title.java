package org.cubeville.cvtools.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.cubeville.commons.commands.BaseCommand;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandParameterOnlinePlayer;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.utils.ColorUtils;

public class Title extends BaseCommand {

    public Title() {
        super("title");
        addBaseParameter(new CommandParameterOnlinePlayer());
        addBaseParameter(new CommandParameterString());
        addBaseParameter(new CommandParameterString());
        addOptionalBaseParameter(new CommandParameterInteger());
        addOptionalBaseParameter(new CommandParameterInteger());
        addOptionalBaseParameter(new CommandParameterInteger());
        addFlag("message");
        setPermission("cvtools.title");
    }

    @Override
    public CommandResponse execute(CommandSender sender, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        Player player = (Player) baseParameters.get(0);
        String title = ColorUtils.addColorWithoutHeader((String) baseParameters.get(1));
        String subtitle = ColorUtils.addColorWithoutHeader((String) baseParameters.get(2));

        int fadeIn = 5;
        int fadeOut = 5;
        int stay = 40;
        if(baseParameters.size() >= 4) {
            stay = (Integer) baseParameters.get(3);
            if(baseParameters.size() >= 5) {
                fadeIn = (Integer) baseParameters.get(4);
                if(baseParameters.size() >= 6) fadeOut = (Integer) baseParameters.get(5);
            }
        }

        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);

        if(flags.contains("message")) {
            if(title.equals("")) {
                player.sendMessage(subtitle);
            }
            else if(subtitle.equals("")) {
                player.sendMessage(title);
            }
            else {
                player.sendMessage(title + "§r - " + subtitle);
            }
        }
        
        return null;
    }
}
