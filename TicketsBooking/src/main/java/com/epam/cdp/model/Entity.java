package com.epam.cdp.model;

public interface Entity extends Cloneable {
    /**
     * Event id. UNIQUE.
     *
     * @return Event Id
     */
    long getId();

    void setId(long id);

    Entity clone();
}
