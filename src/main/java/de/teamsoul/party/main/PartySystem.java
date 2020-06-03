/*
 * Copyright (c) 2020.
 * Plugin by Lucas L. - CompileException..
 */

package de.teamsoul.party.main;

import de.teamsoul.party.config.Configuration;
import de.teamsoul.party.listener.ServerSwitch;
import de.teamsoul.party.utils.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.HashMap;

public class PartySystem extends Plugin {

    Configuration configuration = new Configuration("MAIN");
    public String hostname;
    public int port;
    public String username;
    public String password;
    public String database;

    private static PartySystem instance;
    public HashMap<ProxiedPlayer, PartyManager> manager;

    @Override
    public void onEnable() {
        System.out.println("[Party] System gestartet!");
        System.out.println("Version: " + getDescription().getVersion());

        try {
            register();
            fetchingData();
            loadconfiguration();

            new File("./plugins/Party/").mkdir();

        } catch (Exception e) {
            System.out.println("FEHLER BEI STARTEN!");
            System.out.println("SYSTEM STOPPT!");
        }

    }

    @Override
    public void onDisable() {
        System.out.println("[Party] System gestoppt!");

    }

    public void register() {
        new de.teamsoul.party.commands.partychatcommand(this);
        new de.teamsoul.party.commands.partycommand(this);
        new ServerSwitch(this);
    }

    public void fetchingData() {
        manager = new HashMap<>();
        instance = this;
    }


    public void loadconfiguration() {
        if (!new File("./plugins/Party/").exists()) {
            configuration.append("mysql.Host", "localhost");
            configuration.append("mysql.Port", 3306);
            configuration.append("mysql.User", "root");
            configuration.append("mysql.Password", "pw");
            configuration.append("mysql.Database", "Party");
            configuration.saveAsConfig("./plugins/Party/");
        } else {
            configuration.loadToExistingConfiguration(new File("./plugins/Party"));
            hostname = configuration.getString("mysql.Host");
            port = configuration.getInt("mysql.Port");
            database = configuration.getString("mysql.Database");
            password = configuration.getString("mysql.Password");
            username = configuration.getString("mysql.User");
            configuration.saveAsConfig("./plugins/Party/");
        }
    }

    public static PartySystem getInstance() {
        return instance;
    }
}
