package org.cubeville.commons.bukkit.command;

import java.lang.IllegalArgumentException;

public class CommandParameterEnum implements CommandParameterType
{
    @SuppressWarnings("rawtypes")
    Class<? extends Enum> enumType;

    public CommandParameterEnum(Class<? extends Enum<?>> enumType) {
        this.enumType = enumType;
    }
    
    @SuppressWarnings({ "unchecked", "unused" })
    public boolean isValid(String value) {
        try {
            Enum<?> theOneAndOnly = Enum.valueOf(enumType, value.toUpperCase());
        }
        catch(IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public String getInvalidMessage(String value) {
        return value + " is no valid value!";
    }

    @SuppressWarnings("unchecked")
    public Object getValue(String value) {
        return Enum.valueOf(enumType, value.toUpperCase());
    }

    
}
