package com.example.day17.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class GameService {

    @Autowired
    private GameRepo gameRepo;

    public JsonObject getGID(String gid) {
        String result = gameRepo.getGame(gid);
        try (InputStream inputStream = new ByteArrayInputStream(
                result.getBytes())) {
            JsonReader reader = Json.createReader(inputStream);
            JsonObject data = reader.readObject();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonArray searchGID(String gid) {
        Collection<String> result = gameRepo.searchGame(gid);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        result.stream()
                .filter((String gameString) -> {
                    try (InputStream inputStream = new ByteArrayInputStream(gameString.getBytes())) {
                        JsonReader reader = Json.createReader(inputStream);
                        JsonObject data = reader.readObject();
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                })
                .map((String gameString) -> {
                    InputStream inputStream = new ByteArrayInputStream(gameString.getBytes());
                    JsonReader reader = Json.createReader(inputStream);
                    JsonObject data = reader.readObject();
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return data;
                })
                .forEach((JsonObject obj) -> {
                    arrayBuilder.add(obj);
                });
        JsonArray resultArray = arrayBuilder.build();
        return resultArray;
    }

    public JsonArray searchGameArray(String pattern) {
        Set<String> keySet = gameRepo.getKeys(pattern);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        keySet.stream()
                .map((String key) -> {
                    return "/game/" + key;
                })
                .forEach((String key) -> {
                    arrayBuilder.add(key);
                });
        return arrayBuilder.build();
    }
}
