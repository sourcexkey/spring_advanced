package com.epam.cdp.model;

/**
 * Created by maksym_govorischev.
 */
public interface Ticket extends Entity {
    enum Category {STANDARD, PREMIUM, BAR}

    long getEventId();

    void setEventId(long eventId);

    long getUserId();

    void setUserId(long userId);

    Category getCategory();

    void setCategory(Category category);

    int getPlace();

    void setPlace(int place);

    Ticket clone();
}
