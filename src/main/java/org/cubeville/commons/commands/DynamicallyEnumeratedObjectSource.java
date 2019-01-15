package org.cubeville.commons.commands;

public interface DynamicallyEnumeratedObjectSource
{
    public boolean containsObject(String value);
    public Object getObject(String value);
}
