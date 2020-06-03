package de.teamsoul.party.config;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Configuration {

    protected static final Gson GSON = (new GsonBuilder()).serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
    protected static final JsonParser PARSER = new JsonParser();
    protected String name;
    protected JsonObject dataCatcher;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Configuration(String name) {
        this.name = name;
        this.dataCatcher = new JsonObject();
    }


    public Configuration(String name, JsonObject source) {
        this.name = name;
        this.dataCatcher = source;
    }


    public Configuration(Configuration defaults) {
        this.dataCatcher = defaults.dataCatcher;
    }


    public Configuration(Configuration defaults, String name) {
        this.dataCatcher = defaults.dataCatcher;
        this.name = name;
    }


    public Configuration() {
        this.dataCatcher = new JsonObject();
    }


    public Configuration(JsonObject source) {
        this.dataCatcher = source;
    }


    public JsonObject obj() {
        return this.dataCatcher;
    }


    public boolean contains(String key) {
        return this.dataCatcher.has(key);
    }


    public Configuration append(String key, String value) {
        this.dataCatcher.addProperty(key, value);
        return this;
    }


    public Configuration append(String key, Number value) {
        this.dataCatcher.addProperty(key, value);
        return this;
    }


    public Configuration append(String key, Boolean value) {
        this.dataCatcher.addProperty(key, value);
        return this;
    }


    public Configuration append(String key, JsonElement value) {
        this.dataCatcher.add(key, value);
        return this;
    }

    // Lists are not supported ._.

    public Configuration append(String key, Configuration value) {
        this.dataCatcher.add(key, value.dataCatcher);
        return this;
    }


    @Deprecated
    public Configuration append(String key, Object value) {
        if (value == null) return this;
        this.dataCatcher.add(key, GSON.toJsonTree(value));
        return this;
    }


    public Configuration remove(String key) {
        this.dataCatcher.remove(key);
        return this;
    }


    public Set<String> keys() {
        Set<String> c = new HashSet<>();

        for (Map.Entry<String, JsonElement> x : this.dataCatcher.entrySet()) {
            c.add(x.getKey());
        }

        return c;
    }


    public String getString(String key) {
        if (!this.dataCatcher.has(key)) return "";
        return this.dataCatcher.get(key).getAsString();
    }


    public int getInt(String key) {
        if (!this.dataCatcher.has(key)) return 0;
        return this.dataCatcher.get(key).getAsInt();
    }


    public long getLong(String key) {
        if (!this.dataCatcher.has(key)) return 0L;
        return this.dataCatcher.get(key).getAsLong();
    }


    public double getDouble(String key) {
        if (!this.dataCatcher.has(key)) return 0.0D;
        return this.dataCatcher.get(key).getAsDouble();
    }


    public boolean getBoolean(String key) {
        if (!this.dataCatcher.has(key)) return false;
        return this.dataCatcher.get(key).getAsBoolean();
    }


    public float getFloat(String key) {
        if (!this.dataCatcher.has(key)) return 0.0F;
        return this.dataCatcher.get(key).getAsFloat();
    }


    public short getShort(String key) {
        if (!this.dataCatcher.has(key)) return 0;
        return this.dataCatcher.get(key).getAsShort();
    }

    public <T> T getObject(String key, Class<T> class_) {
        if (!this.dataCatcher.has(key)) return null;
        JsonElement element = this.dataCatcher.get(key);

        return (T) GSON.fromJson(element, class_);
    }


    public Configuration getConfiguration(String key) {
        return new Configuration(this.dataCatcher.get(key).getAsJsonObject());
    }


    public JsonArray getArray(String key) {
        return this.dataCatcher.get(key).getAsJsonArray();
    }


    public String convertToJson() {
        return GSON.toJson(this.dataCatcher);
    }


    public String convertToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this.dataCatcher);
    }


    public boolean saveAsConfig(File backend) {
        if (backend == null) return false;

        if (backend.exists()) {
            backend.delete();
        }

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(backend), "UTF-8")) {

            GSON.toJson(this.dataCatcher, writer);
            return true;
        } catch (IOException ex) {
            ex.getStackTrace();

            return false;
        }
    }


    public boolean saveAsConfig(String path) {
        return saveAsConfig(new File(path));
    }

    public static Configuration loadConfiguration(File backend) {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(backend), "UTF-8")) {
            JsonObject object = PARSER.parse(new BufferedReader(reader)).getAsJsonObject();
            return new Configuration(object);
        } catch (Exception ex) {
            ex.getStackTrace();

            return new Configuration();
        }
    }

    public Configuration loadToExistingConfiguration(File backend) {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(backend), "UTF-8")) {

            this.dataCatcher = PARSER.parse(reader).getAsJsonObject();

            return this;
        } catch (Exception ex) {
            ex.getStackTrace();

            return new Configuration();
        }
    }

    public static Configuration load(String input) {
        try (InputStreamReader reader = new InputStreamReader(new StringBufferInputStream(input), "UTF-8")) {

            return new Configuration(PARSER.parse(new BufferedReader(reader)).getAsJsonObject());
        } catch (IOException e) {

            e.printStackTrace();

            return new Configuration();
        }
    }


    public static Configuration load(JsonObject input) {
        return new Configuration(input);
    }


    public <T> T getObject(String key, Type type) {
        return (T) GSON.fromJson(this.dataCatcher.get(key), type);
    }
}
