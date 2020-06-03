/*
 * Copyright (c) 2020.
 * Plugin by Lucas L. - CompileException
 */

package de.teamsoul.party.utils;

import de.teamsoul.party.main.Datasave;
import de.teamsoul.party.main.PartySystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyThread extends Thread{

    private int count = 0;
    private PartyManager manager;
    private PartySystem main;

    public PartyThread(PartyManager manager, PartySystem main) {
        this.manager = manager;
        this.main = main;
    }

    @Override
    public void run() {
        for(int i = 0; i>-1; i++) {
            try {
                if(manager.getMembers().size() > 1) {
                    this.stop();
                }
                if(i==25) {
                    if(manager.getMembers().size() == 1) {
                        manager.sendPartyMessage(Datasave.prefix + "§7Da niemand die Party betreten hat wurde sie §4aufgelöst§7!");
                        main.manager.remove(manager.getOwner());
                        for (ProxiedPlayer all : manager.getInvites()) {
                            main.manager.remove(all);
                        }

                    }
                    this.stop();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
