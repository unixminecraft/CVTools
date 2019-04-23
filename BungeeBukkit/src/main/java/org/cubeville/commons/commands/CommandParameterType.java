package org.cubeville.commons.commands;

public interface CommandParameterType
{
    public boolean isValid(String value);
    public String getInvalidMessage(String value);
    public Object getValue(String value);
}
