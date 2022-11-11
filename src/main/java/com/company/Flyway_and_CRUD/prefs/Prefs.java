package com.company.Flyway_and_CRUD.prefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Prefs {
    public final static String DB_JDBC_CONNECTION_URL="dbUrl";
    public final static String DEFAULT_PREFS_FILENAME="prefs.json";
    private Map<String, Object> prefs=new HashMap<>();

    public Prefs(){
        this(DEFAULT_PREFS_FILENAME);
    }
    public Prefs (String filename){
        try {
            String json = String.join("\n", Files.readAllLines(Paths.get(filename)));
            TypeToken<?> typeToken = TypeToken.getParameterized(
                    Map.class,
                    String.class,
                    Object.class
            );
            prefs = new Gson().fromJson(json, typeToken.getType());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public String getString(String key){
        return getPref(key).toString();
    }

    public Object getPref(String key){
        return prefs.get(key);
    }
}
