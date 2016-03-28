package com.epam.cdp.services.impl;

import com.epam.cdp.dao.EventDAO;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.impl.EventEntity;
import com.epam.cdp.model.impl.Events;
import com.epam.cdp.services.EventService;
import com.epam.cdp.services.utils.ArgsCheckingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

public class EventServiceImpl implements EventService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private EventDAO eventDAO;
    @Autowired
    @Qualifier("eventMarshaller")
    private CastorMarshaller unmarshaller;
    @Autowired
    private TransactionTemplate tTemplate;

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

    @Override
    public void loadEventsFromFile(final InputStream is) {
        tTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                List<EventEntity> events;
                try {
                    Events unmarshall = (Events) unmarshaller.unmarshal(new StreamSource(is));
                    events = unmarshall.getEvents();
                } catch (IOException e) {
                    LOG.warn("IOException:", e);
                    return;
                }
                for (EventEntity e : events) {
                    eventDAO.create(e);
                }
            }
        });
    }
}
