package org.cubeville.commons.bukkit.command;

import java.util.ArrayList;
import java.util.List;

public class CommandParameterListEnum implements CommandParameterType
{
    @SuppressWarnings("rawtypes")
    Class<? extends Enum> enumType;
    
    public CommandParameterListEnum(Class<? extends Enum<?>> enumType) {
        this.enumType = enumType;
    }

    @SuppressWarnings("unchecked")
    public boolean isValid(String value) {
        String[] parts = value.split(";");
        try {
            for(int i = 0; i < parts.length; i++) {
                Enum.valueOf(enumType, parts[i].toUpperCase());
            }
        }
        catch(IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public String getInvalidMessage(String value) {
        return value + " is no valid enum list!";
    }

    @SuppressWarnings("unchecked")
    public Object getValue(String value) {
        String[] parts = value.split(";");
        List<Enum<?>> ret = new ArrayList<>();
        for(int i = 0; i < parts.length; i++) {
            ret.add(Enum.valueOf(enumType, parts[i].toUpperCase()));
        }
        return ret;
    }
}
