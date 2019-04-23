package org.cubeville.commons.bungeebukkit.command;

public interface DynamicallyEnumeratedObjectSource
{
    public boolean containsObject(String value);
    public Object getObject(String value);
}
