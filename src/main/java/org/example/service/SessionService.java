package org.example.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class SessionService {

    private final StringRedisTemplate redisTemplate;

    public SessionService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createSession(UUID userId) {
        String sessionId = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("SESSION:" + sessionId, userId.toString(), Duration.ofMinutes(30));
        return sessionId;
    }

    public UUID getUserIdBySession(String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new RuntimeException("Oturum açmanız gerekiyor!");
        }
        String userIdStr = redisTemplate.opsForValue().get("SESSION:" + sessionId);
        if (userIdStr == null) {
            throw new RuntimeException("Oturum süresi dolmuş veya geçersiz oturum!");
        }
        return UUID.fromString(userIdStr);
    }

    public void invalidateSession(String sessionId) {
        redisTemplate.delete("SESSION:" + sessionId);
    }
}