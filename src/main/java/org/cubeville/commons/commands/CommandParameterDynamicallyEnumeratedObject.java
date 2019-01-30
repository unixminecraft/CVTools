package org.cubeville.commons.commands;

public class CommandParameterDynamicallyEnumeratedObject implements CommandParameterType
{
    DynamicallyEnumeratedObjectSource source;
    
    public CommandParameterDynamicallyEnumeratedObject(DynamicallyEnumeratedObjectSource source) {
        this.source = source;
    }

    public boolean isValid(String value) {
        return source.containsObject(value);
    }

    public String getInvalidMessage(String value) {
        return value + " is no valid parameter.";
    }

    public Object getValue(String value) {
        return source.getObject(value);
    }
}
