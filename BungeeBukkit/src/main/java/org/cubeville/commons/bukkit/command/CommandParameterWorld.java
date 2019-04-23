package org.cubeville.commons.bukkit.command;

import org.bukkit.Bukkit;

public class CommandParameterWorld implements CommandParameterType
{
    public boolean isValid(String value) {
        return Bukkit.getWorld(value) != null;
    }

    public String getInvalidMessage(String value) {
        return value + " is no existing world!";
    }

    public Object getValue(String value) {
        return Bukkit.getWorld(value);
    }
}
