package com.epam.cdp.services.impl;

import com.epam.cdp.dao.EventDAO;
import com.epam.cdp.model.Event;
import com.epam.cdp.services.EventService;
import com.epam.cdp.services.utils.ArgsCheckingUtils;

import java.util.Date;
import java.util.List;

public class EventServiceImpl implements EventService {
    private EventDAO eventDAO;

    @Override
    public Event getEventById(long eventId) {
        return eventDAO.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        ArgsCheckingUtils.checkPageSize(pageSize);
        ArgsCheckingUtils.checkPageNum(pageNum);
        return eventDAO.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        ArgsCheckingUtils.checkPageSize(pageSize);
        ArgsCheckingUtils.checkPageNum(pageNum);
        return eventDAO.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        ArgsCheckingUtils.checkIsNull(event);
        return eventDAO.create(event);
    }

    @Override
    public Event updateEvent(Event event) {
        ArgsCheckingUtils.checkIsNull(event);
        ArgsCheckingUtils.checkIsIdSet(event);
        return eventDAO.update(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventDAO.delete(eventId);
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
}
