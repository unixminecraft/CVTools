package org.cubeville.commons.bukkit.command;

public interface DynamicallyEnumeratedObjectSource
{
    public boolean containsObject(String value);
    public Object getObject(String value);
}
