package de.teamsoul.party.config;

import com.google.gson.JsonElement;

import java.io.File;
import java.util.Set;

public interface IConfiguration {

    <T extends IConfiguration> T append(String paramString1, String paramString2);

    <T extends IConfiguration> T append(String paramString, Number paramNumber);

    <T extends IConfiguration> T append(String paramString, Boolean paramBoolean);

    <T extends IConfiguration> T append(String paramString, JsonElement paramJsonElement);

    <T extends IConfiguration> T remove(String paramString);

    Set<String> keys();

    String getString(String paramString);

    int getInt(String paramString);
    double getDouble(String paramString);
    long getLong(String paramString);
    float getFloat(String paramString);
    short getShort(String paramString);

    boolean getBoolean(String paramString);

    String convertToJson();

    boolean saveAsConfig(File file);
    boolean saveAsConfig(String paramString);

    <T extends IConfiguration> T getConfiguration(String paramString);
}
