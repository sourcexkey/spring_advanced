package com.epam.cdp.model.impl;

import com.epam.cdp.model.Event;
import com.epam.cdp.model.User;

public class DefaultUserEvent {
    private User defaultUser;
    private Event defaultEvent;

    public User getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(User defaultUser) {
        this.defaultUser = defaultUser;
    }

    public Event getDefaultEvent() {
        return defaultEvent;
    }

    public void setDefaultEvent(Event defaultEvent) {
        this.defaultEvent = defaultEvent;
    }
}
