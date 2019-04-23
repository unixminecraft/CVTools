package org.cubeville.commons.bukkit.command;

import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.List;

public class CommandParameterListDouble implements CommandParameterType
{
    Integer length;
    String delimiter;
    
    public CommandParameterListDouble(String delimiter) {
        this.delimiter = delimiter;
    }
    
    public CommandParameterListDouble(int length) {
        this.length = length;
        this.delimiter = ";";
    }

    public CommandParameterListDouble(int length, String delimiter) {
        this.length = length;
        this.delimiter = delimiter;
    }
    
    public boolean isValid(String value) {
        String[] parts = value.split(delimiter);
        if(length != null && parts.length != length) return false;
        try {
            for(int i = 0; i < parts.length; i++) {
                Double.valueOf(parts[i]);
            }
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public String getInvalidMessage(String value) {
        String[] parts = value.split(delimiter);
        if(length != null && parts.length != length) return value + " has not the correct length of " + length;
        return "No valid number.";
    }

    public Object getValue(String value) {
        List<Double> ret = new ArrayList<>();
        String[] parts = value.split(delimiter);
        for(int i = 0; i < parts.length; i++) {
            ret.add(Double.valueOf(parts[i]));
        }
        return ret;
    }
}
