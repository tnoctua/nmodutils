package me.tnoctua.nmodutils.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigHandler {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File CONFIG_FILE;
    private final Supplier<JsonObject> TO_JSON;
    private final Consumer<JsonObject> FROM_JSON;
    private BufferedReader br;
    private BufferedWriter bw;

    /**
     * Instance of a configuration file.
     *
     * @param path path to the configuration file relative to config directory and without the file extension
     * @param toJson method to serialize the configuration into a JSON Object
     * @param fromJson method to deserialize the configuration from a JSON Object
     */
    public ConfigHandler(String path, Supplier<JsonObject> toJson, Consumer<JsonObject> fromJson) {
        CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(path + ".json").toFile();
        TO_JSON = toJson;
        FROM_JSON = fromJson;
        if (CONFIG_FILE.exists()) {
            load();
            save();
        } else {
            save();
            load();
        }
    }

    /**
     * Saves the configuration to disk.
     */
    public void save() {
        write(TO_JSON.get());
    }

    /**
     * Loads configuration from disk.
     */
    public void load() {
        FROM_JSON.accept(read());
    }

    /**
     * Writes the provided JSON object to the configuration file.
     * @param json object to write
     */
    public void write(JsonObject json) {
        // Write File
        try {
            if (bw != null) {
                bw.close();
            }
            bw = new BufferedWriter(new FileWriter(CONFIG_FILE));
            GSON.toJson(json, bw);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the configuration file and returns a JSON object representation.
     * @return file as JSON
     */
    public JsonObject read() {
        // Read File
        try {
            if (br != null) {
                br.close();
            }
            br = new BufferedReader(new FileReader(CONFIG_FILE));
            JsonObject json = GSON.fromJson(br, JsonObject.class);
            br.close();
            return json;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
