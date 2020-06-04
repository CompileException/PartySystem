/*
 * Copyright (c) 2020.
 * Plugin by Lucas L. - CompileException..
 */

package de.teamsoul.party.main;

import de.teamsoul.party.listener.ServerSwitch;
import de.teamsoul.party.mysql.MySQL;
import de.teamsoul.party.utils.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class PartySystem extends Plugin {

    public String hostname;
    public int port;
    public String username;
    public String password;
    public String database;

    private static PartySystem instance;
    public HashMap<ProxiedPlayer, PartyManager> manager;

    //MYSQL DATA
    public static MySQL mySQL = new MySQL("localhost", 3306, "party", "root", "");

    @Override
    public void onEnable() {
        System.out.println("[Party] System gestartet!");
        System.out.println("Version: " + getDescription().getVersion());

        try {
            register();
            fetchingData();
            loadconfiguration();
            mysqlconnect();
        } catch (Exception e) {
            System.out.println("FEHLER BEI STARTEN!");
            System.out.println("SYSTEM STOPPT!");
        }

    }

    @Override
    public void onDisable() {
        System.out.println("[Party] System gestoppt!");

        mysqldisconnect();
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
        try {

        } catch (Exception e) { }
    }

    public void mysqlconnect() {
        try {
            mySQL.openConnection();
            System.out.println(SystemColor.GREEN + "MySQL connected!");
        } catch (Exception e) {
            System.out.println(SystemColor.red + "Fehler beim verbinden der MySQL!");
            e.printStackTrace(); }
    }

    public void mysqldisconnect() {
        try {
            mySQL.closeConnection();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static PartySystem getInstance() {
        return instance;
    }
}
