package de.teamsoul.party.mysql;

import de.teamsoul.party.main.PartySystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLManager {

    public static boolean doEnablePartyRequests(String Name) {
        try {
            Connection connection = PartySystem.mySQL.openConnection().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Friends WHERE Name = ? AND AllowParty = true");
            ps.setString(1, Name);
            ResultSet rs = ps.executeQuery();

            boolean isNext = rs.next();
            connection.close();

            return isNext;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
