package de.teamsoul.party.listener;

import de.teamsoul.party.main.Datasave;
import de.teamsoul.party.main.PartySystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitch implements Listener {

    private PartySystem main;

    public ServerSwitch(PartySystem main) {
        this.main = main;
        main.getProxy().getPluginManager().registerListener(main, this);
    }

    @EventHandler
    public void callEvent(ServerSwitchEvent e) {

        ProxiedPlayer p = e.getPlayer();
        if (main.manager.containsKey(p)) {
            if (main.manager.get(p).getOwner().equals(p)) {
                for (ProxiedPlayer player : main.manager.get(p).getMembers()) {
                    if (player != main.manager.get(p).getOwner()) {
                        player.connect(e.getPlayer().getServer().getInfo());
                    }
                }
                main.manager.get(p).sendPartyMessage(Datasave.prefix + "§7Die Party wechselt nun den Server auf: §e" + e.getPlayer().getServer().getInfo().getName());
            }
        }
    }
}
