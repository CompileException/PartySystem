package de.teamsoul.party.commands;

import de.teamsoul.party.main.Datasave;
import de.teamsoul.party.main.PartySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.event.EventHandler;

public class partychatcommand extends Command {

    private PartySystem main;

    public partychatcommand(PartySystem main) {
        super("pc");
        this.main = main;
        main.getProxy().getPluginManager().registerCommand(main, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer p = (ProxiedPlayer) sender;
    }

    @EventHandler
    public void callEvent(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String msg = e.getMessage();
        if (msg == null) {
            p.sendMessage(Datasave.prefix + "§7Bitte schreibe etwas!");

        } else {
            if (main.manager.containsKey(p)) {
                if (main.manager.get(p).getMembers().contains(p)) {
                    if (main.manager.get(p).getOwnerAsPrefix().equalsIgnoreCase(p.getName())) {
                        main.manager.get(p).sendPartyMessage(Datasave.prefix + "§cParty-Leiter §7× §c" + p.getName() + "§7: " + msg);

                    } else {
                        main.manager.get(p).sendPartyMessage(Datasave.prefix + p.getName() + ": " + msg);
                    }
                } else {
                    p.sendMessage(Datasave.prefix + "§7Du bist in §ckeiner §7Party!");
                }
            } else {
                p.sendMessage(Datasave.prefix + "§7Du bist in §ckeiner §7Party!");
            }
        }
    }
}
