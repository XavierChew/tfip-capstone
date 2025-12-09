package com.gotbufffetleh.backend.components;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Component to keep track of all the currently active user sessions.
 */

@Component
public class SessionRegistry {
    private final Map<String, SessionScopedCounter> sessions = new ConcurrentHashMap<>();

    public void register(SessionScopedCounter session) {
        sessions.put(session.getSessionId(), session);
    }

    public void unregister(SessionScopedCounter session) {
        sessions.remove(session.getSessionId());
    }

    public Map<String, SessionScopedCounter> getAllSessions() {
        return sessions;
    }
}

