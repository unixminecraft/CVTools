package org.cubeville.commons.bukkit.command;

import java.util.ArrayList;
import java.util.List;

public class CommandParameterList implements CommandParameterType
{
    private List<CommandParameterType> parameters;

    public CommandParameterList(List<CommandParameterType> parameters) {
        this.parameters = parameters;
    }

    public boolean isValid(String value) {
        List<String> parts = splitParameterList(value);
        if(parts.size() != parameters.size()) return false;
        for(int i = 0; i < parts.size(); i++) {
            if(!parameters.get(i).isValid(parts.get(i))) return false;
        }
        return true;
    }

    public String getInvalidMessage(String value) {
        return "No valid parameter list!";
    }

    public Object getValue(String value) {
        List<Object> ret = new ArrayList<>();
        List<String> parts = splitParameterList(value);
        for(int i = 0; i < parts.size(); i++) {
            ret.add(parameters.get(i).getValue(parts.get(i)));
        }
        return ret;
    }

    private static List<String> splitParameterList(String value) {
        List<String> ret = new ArrayList<>();
        String lastpar = "";
        int inPara = 0;
        for(int p = 0; p < value.length(); p++) {
            if(value.charAt(p) == '(') {
                inPara++;
                lastpar += "(";
            }
            else if(value.charAt(p) == ')') {
                inPara--;
                lastpar += ")";
            }
            else if(value.charAt(p) == ';' && inPara == 0) {
                ret.add(lastpar);
                lastpar = "";
            }
            else {
                lastpar += value.charAt(p);
            }
        }
        ret.add(lastpar);
        return ret;
    }
}
