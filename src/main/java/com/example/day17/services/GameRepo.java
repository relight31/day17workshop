package com.example.day17.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepo {
    Logger logger = Logger.getLogger(GameRepo.class.getName());

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public String getGame(String gid) {
        String result = (String) redisTemplate.opsForHash()
                .get(gid, "rec");
        return result;
    }

    public Collection<String> searchGame(String gid) {

        List<String> result = redisTemplate.opsForHash()
                .values(gid)
                .stream()
                .map(String.class::cast)
                .toList();
        logger.log(Level.INFO, "Retrieved " + result.size() + " records");
        return result;
    }

    public Set<String> getKeys(String pattern) {
        Set<String> keySet = redisTemplate.keys("*" + pattern + "*");
        logger.log(Level.INFO, "Retrieved " + keySet.size() + " records");
        return keySet;
    }
}
