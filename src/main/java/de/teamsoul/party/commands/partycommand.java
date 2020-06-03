/*
 * Copyright (c) 2020.
 * Plugin by Lucas L. - CompileException.
 */

package de.teamsoul.party.commands;

import de.teamsoul.party.main.Datasave;
import de.teamsoul.party.main.PartySystem;
import de.teamsoul.party.utils.PartyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class partycommand extends Command {

    private PartySystem main;

    public partycommand(PartySystem main) {
        super("party");
        this.main = main;
        main.getProxy().getPluginManager().registerCommand(main, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer p = (ProxiedPlayer) sender;


        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("invite")) {
                ProxiedPlayer tar = ProxyServer.getInstance().getPlayer(args[1]);
                if (tar == null) {
                    p.sendMessage(Datasave.prefix + "§cDieser Spieler ist offline.");
                    return;
                }
                if (this.main.manager.containsKey(p)) {
                    PartyManager manager = this.main.manager.get(p);
                    if (manager.getOwner().equals(p)) {
                        if (!main.manager.containsKey(tar)) {
                            if (!manager.getMembers().contains(p)) {
                                if (!manager.getInvites().contains(p)) {
                                    manager.sendInvite(tar);
                                } else {
                                    p.sendMessage(Datasave.prefix + "§7Der Spieler ist bereits in der Party oder hat schon eine Anfrage erhalten.");
                                }
                            } else {
                                p.sendMessage(Datasave.prefix + "§7Der Spieler ist bereits in der Party oder hat schon eine Anfrage erhalten.");
                            }
                        } else {
                            p.sendMessage(Datasave.prefix + "§cDer Spieler ist bereits in einer Party");
                        }
                    } else {
                        p.sendMessage(Datasave.prefix + "§4Du bist nicht der Besitzer dieser Party");
                    }
                } else {
                    if (!tar.equals(p)) {
                        if (main.manager.containsKey(tar)) {
                            p.sendMessage(Datasave.prefix + "§cDer Spieler ist bereits in einer Party");
                            return;
                        }
                        PartyManager manager = new PartyManager(p, main);
                        manager.sendInvite(tar);
                        main.manager.put(p, manager);
                    } else {
                        p.sendMessage(Datasave.prefix + "§cDu kannst dich nicht selbst einladen");
                    }
                }
            }

        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("accept")) {
                if (main.manager.containsKey(p)) {
                    if (main.manager.get(p).getMembers().contains(p)) {
                        p.sendMessage(Datasave.prefix + "§cDu bist bereits in der Party");
                        return;
                    }
                    main.manager.get(p).acceptInvite(p);
                } else {
                    p.sendMessage(Datasave.prefix + "§7Du hast §ckeine §7Anfrage erhalten!");
                }

            } else if (args[0].equalsIgnoreCase("deny")) {
                if (main.manager.containsKey(p)) {
                    if (main.manager.get(p).getInvites().contains(p)) {
                        main.manager.get(p).denyInvite(p);
                        p.sendMessage(Datasave.prefix + "§7Du hast die Anfrage §cabgelehnt§7!");
                    } else {
                        p.sendMessage(Datasave.prefix + "§7Du hast §ckeine §7Anfrage erhalten!");
                    }
                } else {
                    p.sendMessage(Datasave.prefix + "§7Du hast §ckeine §7Anfrage erhalten!");
                }

            } else if (args[0].equalsIgnoreCase("leave")) {
                if (main.manager.containsKey(p)) {
                    if (main.manager.get(p).getMembers().contains(p)) {
                        main.manager.get(p).leave(p);
                    } else {
                        p.sendMessage(Datasave.prefix + "§7Du bist in §ckeiner §7Party!");
                    }
                } else {
                    p.sendMessage(Datasave.prefix + "§7Du bist in §ckeiner §7Party!");
                }

            } else if (args[0].equalsIgnoreCase("list")) {
                if (main.manager.containsKey(p)) {
                    if (main.manager.get(p).getMembers().contains(p)) {
                        p.sendMessage(Datasave.prefix + "§cParty - Leiter:");
                        p.sendMessage(Datasave.prefix + "§7- §c" + main.manager.get(p).getOwner().getName());
                        p.sendMessage(" ");
                        p.sendMessage(Datasave.prefix + "§eParty - Mitglieder §7(§e" + String.valueOf(main.manager.get(p).getMembers().size() - 1) + "§7)");

                        for (ProxiedPlayer player : main.manager.get(p).getMembers()) {
                            if (player != main.manager.get(p).getOwner()) {
                                p.sendMessage(Datasave.prefix + "§7- §e" + player.getName());
                            }
                        }
                    } else {
                        p.sendMessage(Datasave.prefix + "§7Du bist in §ckeiner §7Party!");
                    }
                } else {
                    p.sendMessage(Datasave.prefix + "§7Du bist in §ckeiner §7Party!");
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                p.sendMessage("§b/party invite <Name> §7- Lade einen Spieler in deine Party ein!");
                p.sendMessage("§b/party accept §7- Akzeptiere eine Party-Anfrage!");
                p.sendMessage("§b/party deny §7- Lehne eine Party-Anfrage ab!");
                p.sendMessage("§b/party leave §7- Verlasse die momentane Party!");
                p.sendMessage("§b/party list §7- Lass dir alle Mitglieder deiner Party anzeigen!");
                p.sendMessage("§b/party help §7- Lass dir alle Commands vom Party-Plugin zeigen!");
                p.sendMessage("§b/pc §7<Nachricht> §7- Sende eine Nachricht in den Party-Chat!");
            }
        } else {
            p.sendMessage("§b/party invite <Name> §7- Lade einen Spieler in deine Party ein!");
            p.sendMessage("§b/party accept §7- Akzeptiere eine Party-Anfrage!");
            p.sendMessage("§b/party deny §7- Lehne eine Party-Anfrage ab!");
            p.sendMessage("§b/party leave §7- Verlasse die momentane Party!");
            p.sendMessage("§b/party list §7- Lass dir alle Mitglieder deiner Party anzeigen!");
            p.sendMessage("§b/party help §7- Lass dir alle Commands vom Party-Plugin zeigen!");
            p.sendMessage("§b/pc §7<Nachricht> §7- Sende eine Nachricht in den Party-Chat!");
        }

    }
}
