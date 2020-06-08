package de.teamsoul.party.listener;

import de.teamsoul.party.main.Datasave;
import de.teamsoul.party.main.PartySystem;
import de.teamsoul.party.utils.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinQuitManager implements Listener {

    private PartySystem main;


    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if(main.manager.containsKey(player)) {
            main.manager.remove(player);
            player.sendMessage(Datasave.prefix + "§cDa du die Verbindung getrennt hast, wurdest du aus der Party geworfen!");
        }
    }
}
