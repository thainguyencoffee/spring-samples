package com.manning.sbip.ch06.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS_COUNT = 3;

    private LoadingCache<String, Integer> loginAttemptCache;

    // Tạo bộ nhớ cache và hết hạn nội dung của cache sau một ngày.
    public LoginAttemptService() {
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    public void loginSuccess(String username) {
        loginAttemptCache.invalidate(username);
    }

    public void loginFailed(String username) {
        int failedAttemptCounter = 0;
        try {
            failedAttemptCounter = loginAttemptCache.get(username);
        } catch (ExecutionException ex) {
            failedAttemptCounter = 0;
        }
        failedAttemptCounter++;
        loginAttemptCache.put(username, failedAttemptCounter);
    }

    public boolean isBlocked(String username) {
        try {
            return loginAttemptCache.get(username) >= MAX_ATTEMPTS_COUNT;
        } catch (ExecutionException ex) {
            return false;
        }
    }
}
