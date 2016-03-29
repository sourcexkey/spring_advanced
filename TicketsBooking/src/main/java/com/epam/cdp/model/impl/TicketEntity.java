package com.epam.cdp.model.impl;

import com.epam.cdp.model.Ticket;

public class TicketEntity implements Ticket {
    private long id = -1;
    private long eventId;
    private long userId;
    private Category category;
    private int place;

    public TicketEntity() {
    }

    public TicketEntity(long id, long eventId, long userId, Category category, int place) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int getPlace() {
        return place;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (id != that.id) return false;
        if (eventId != that.eventId) return false;
        if (userId != that.userId) return false;
        return place == that.place && category == that.category;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (eventId ^ (eventId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + place;
        return result;
    }

    @Override
    public Ticket clone() {
        TicketEntity newTicket = new TicketEntity();
        newTicket.id = this.id;
        newTicket.eventId = this.eventId;
        newTicket.userId = this.userId;
        newTicket.category = this.category;
        newTicket.place = this.place;
        return newTicket;
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", category=" + category +
                ", place=" + place +
                '}';
    }


}
