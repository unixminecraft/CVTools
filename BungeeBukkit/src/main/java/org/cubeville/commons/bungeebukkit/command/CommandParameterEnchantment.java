package org.cubeville.commons.bungeebukkit.command;

import org.bukkit.enchantments.Enchantment;

public class CommandParameterEnchantment implements CommandParameterType
{
    public boolean isValid(String value) {
        return Enchantment.getByName(value.toUpperCase()) != null;
    }

    public String getInvalidMessage(String value) {
        return value + " is no valid enchantment!";
    }

    public Object getValue(String value) {
        return Enchantment.getByName(value.toUpperCase());
    }
}
