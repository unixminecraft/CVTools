package org.cubeville.cvtools.bungeebukkit.command;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.cubeville.commons.bungeebukkit.command.Command;
import org.cubeville.commons.bungeebukkit.command.CommandExecutionException;
import org.cubeville.commons.bungeebukkit.command.CommandResponse;

public class ChatColor extends Command {

    public ChatColor() {
        super("color");
        addFlag("rainbow");
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) 
        throws CommandExecutionException {
        String[] colors = {"1 - &1Dark_Blue      &r2 - &2Dark_Green"
                           ,"3 - &3Dark_Aqua     &r4 - &4Dark_Red"
                           ,"5 - &5Dark_Purple   &r6 - &6Gold"
                           ,"7 - &7Gray            &r8 - &8Dark_Gray"
                           ,"9 - &9Blue             &r0 - &0Black"
                           ,"A - &aGreen           &rB - &bAqua"
                           ,"C - &cRed              &rD - &dLight_Purple"
                           ,"E - &eYellow           &rF - &fWhite"
                           ,"&a--------------------------------"
                           ,"R - &rReset            &rK - &kRandom"
                           ,"L - &lBold          &rO - &oItalic"
                           ,"N - &nUnderline&r       M - &mStrike"};
        String[] rainbows = {"&44&f-&CC&f-&66&f-&EE&f-&22&f-&AA&f-&BB&f-&33&f-&11&f-&99&f-&DD&f-&55"
                             ,"4 - &4Dark_Red        &rC - &cRed"
                             ,"6 - &6Gold               &rE - &eYellow"
                             ,"2 - &2Dark_Green     &rA - &aGreen"
                             ,"B - &bAqua              &r3 - &3Dark_Aqua"
                             ,"1 - &1Dark_Blue       &r9 - &9Blue"
                             ,"D - &dLight_Purple    &r5 - &5Dark_Purple"
                             ,"&a--------------------------------"
                             ,"&FF&f-&77&f-&88&f-&00"
                             ,"F - &fWhite              &r7 - &7Gray"
                             ,"8 - &8Dark_Gray       &r0 - &0Black"};
		
        CommandResponse cr = new CommandResponse();
        cr.setBaseMessage("&c================================");
        if (flags.contains("rainbow")) {
            for (String rainbow: rainbows) {
                cr.addMessage(rainbow);
            }
        } else {
            for (String color: colors) {
                cr.addMessage(color);
            }
        }
        cr.addMessage("&c================================");
        return cr;
    }

}
