/*
 * Copyright (c) 2020.
 * Plugin by Lucas L. - CompileException.
 */

package de.teamsoul.party.utils;

import de.teamsoul.party.main.Datasave;
import de.teamsoul.party.main.PartySystem;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

public class PartyManager {

    private ArrayList<ProxiedPlayer> members;
    private ArrayList<ProxiedPlayer> invites;
    private ProxiedPlayer owner;
    private PartySystem main;

    public PartyManager(ProxiedPlayer owner, PartySystem main) {
        this.owner = owner;
        this.main = main;
        this.members = new ArrayList<ProxiedPlayer>();
        this.invites = new ArrayList<ProxiedPlayer>();
        this.members.add(owner);
        owner.setDisplayName("[Party]" + owner.getName());

        owner.sendMessage(Datasave.prefix + "§7Es wurde eine neue Party §aerstellt§7!");

        Thread manager = new PartyThread(this, main);
        manager.start();

    }

    public ArrayList<ProxiedPlayer> getMembers() {
        return this.members;
    }

    public void denyInvite(ProxiedPlayer player) {
        if (this.members.contains(player)) return;
        if (this.invites.contains(player)) {
            this.invites.remove(player);
            sendPartyMessage(Datasave.prefix + "§c" + player.getName() + " §7hat die Anfrage §cabgelehnt§7!");
            main.manager.remove(player);
        }
    }

    public void acceptInvite(ProxiedPlayer player) {
        if (this.members.contains(player)) return;
        if (this.invites.contains(player)) {
            this.invites.remove(player);
            this.members.add(player);
            sendPartyMessage(Datasave.prefix + "§a" + player.getName() + " §7hat die Party §abetreten§7!");
        }
    }

    public void leave(ProxiedPlayer player) {
        if (player.equals(owner)) {
            sendPartyMessage(Datasave.prefix + "§c" + player.getName() + " §7hat die Party §cverlassen§7!");
            sendPartyMessage(Datasave.prefix + "§7Die Party wurde §4aufgelöst§7!");
            for (ProxiedPlayer all : this.members) {
                main.manager.remove(all);
            }
            for (ProxiedPlayer all : this.invites) {
                main.manager.remove(all);
            }
            return;
        }
        if (this.members.contains(player)) {
            sendPartyMessage(Datasave.prefix + "§c" + player.getName() + " §7hat die Party §cverlassen§7!");
            if (this.members.size() - 1 == 1) {
                this.members.remove(player);
                main.manager.remove(player);
                sendPartyMessage(Datasave.prefix + "§7Da nicht genügend Spieler vorhanden sind, wird die Party §4aufgelöst§7!");
                for (ProxiedPlayer all : this.members) {
                    main.manager.remove(all);
                }
                for (ProxiedPlayer all : this.invites) {
                    main.manager.remove(all);
                }
            } else {
                this.members.remove(player);
                main.manager.remove(player);
            }
        }
    }

    public void sendInvite(ProxiedPlayer player) {
        if (this.members.contains(player)) return;
        if (this.invites.contains(player)) return;
        this.invites.add(player);
        main.manager.put(player, this);
        TextComponent component = new TextComponent();
        component.setText(Datasave.prefix + "§aDu wurdest von " + this.owner.getName() + " in seine Party eingeladen");


        TextComponent deny = new TextComponent();
        deny.setText("§7[§4ABLEHNEN§7]");
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny"));

        TextComponent accept = new TextComponent();
        accept.setText("§7[§2ANNEHMEN§7]");
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept"));

        TextComponent fully = new TextComponent();
        fully.setText(Datasave.prefix);
        fully.addExtra(accept);
        fully.addExtra(" ");
        fully.addExtra(deny);

        player.sendMessage(component);
        player.sendMessage(fully);

    }

    public void addMember(ProxiedPlayer player) {
        this.members.add(player);
    }

    public ArrayList<ProxiedPlayer> getInvites() {
        return this.invites;
    }

    public ProxiedPlayer getOwner() {
        return this.owner;
    }

    public String getOwnerAsPrefix() {
        return this.owner.getName();
    }

    public void addInvite(ProxiedPlayer player) {
        this.invites.add(player);
    }

    public void sendPartyMessage(String message) {
        for (ProxiedPlayer player : this.members) {
            player.sendMessage(message);
        }
    }
}
