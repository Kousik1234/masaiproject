package com.masaiproject.limiter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimiter {
    private final Cache<String, Integer> emailRequestsCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public boolean allow(String emailorphoneno) {
        int requests = emailRequestsCache.get(emailorphoneno, key -> 0);
        if (requests < 3) {
            emailRequestsCache.put(emailorphoneno, requests + 1);
            return true;
        }
        return false;
    }
    
    
}
