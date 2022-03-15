package com.example.day17.controllers;

import com.example.day17.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {
    // a controller accessing a repository directly is called fast lane reader
    @Autowired
    private GameService gameService;

    @GetMapping(path = "/{gid}")
    public ResponseEntity<String> searchByPathVar(
            @PathVariable(name = "gid") String gid) {
        JsonObject result = gameService.getGID(gid);
        return ResponseEntity.ok()
                .body(result.toString());
    }

    @GetMapping(path = "/search")
    public ResponseEntity<String> searchByQueryString(
            @RequestParam String pattern) {
        JsonArray result = gameService.searchGameArray(pattern);
        return ResponseEntity.ok()
                .body(result.toString());
    }
}
