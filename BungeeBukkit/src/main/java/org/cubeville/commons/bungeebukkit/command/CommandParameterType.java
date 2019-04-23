package org.cubeville.commons.bungeebukkit.command;

public interface CommandParameterType
{
    public boolean isValid(String value);
    public String getInvalidMessage(String value);
    public Object getValue(String value);
}
