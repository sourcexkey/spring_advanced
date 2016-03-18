package com.epam.cdp.dao.impl;

import com.epam.cdp.dao.DAOConstants;
import com.epam.cdp.dao.DAOUtils;
import com.epam.cdp.dao.EventDAO;
import com.epam.cdp.dao.impl.mappers.EventMapper;
import com.epam.cdp.model.Event;
import org.skife.jdbi.v2.GeneratedKeys;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.Update;
import org.skife.jdbi.v2.spring.DBIUtil;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.LongMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class EventDAOImpl implements EventDAO {
    private final ResultSetMapper<Event> EVENT_MAPPER = new EventMapper();
    private static final String SELECT_EVENT_BY_ID = "SELECT * FROM events WHERE id=:id";
    private static final String SELECT_EVENT_BY_TITLE = "SELECT * FROM events WHERE title LIKE :title " + DAOConstants.LIMIT_OFFSET;
    private static final String SELECT_EVENT_FOR_DAY = "SELECT * FROM events WHERE day = :day " + DAOConstants.LIMIT_OFFSET;
    private static final String INSERT_EVENT = "INSERT INTO events VALUES(default,:title,:day,:ticket_price)";
    private static final String UPDATE_EVENT = "UPDATE events SET title=:title, day=:day WHERE id=:id";
    private static final String DELETE_EVENT = "DELETE FROM events WHERE id=:id";

    @Autowired
    private IDBI dbi;

    @Override
    public Event getEventById(final long eventId) {
        Handle h = DBIUtil.getHandle(dbi);
        return h.createQuery(SELECT_EVENT_BY_ID)
                .map(EVENT_MAPPER)
                .bind(DAOConstants.ID_PLACEHOLDER, eventId)
                .first();
    }

    @Override
    public List<Event> getEventsByTitle(final String title, final int pageSize, final int pageNum) {
        Handle h = DBIUtil.getHandle(dbi);
        int offset = DAOUtils.calcOffset(pageSize, pageNum);
        return h.createQuery(SELECT_EVENT_BY_TITLE)
                .map(EVENT_MAPPER)
                .bind(DAOConstants.LIMIT_PLACEHOLDER, pageSize)
                .bind(DAOConstants.OFFSET_PLACEHOLDER, offset)
                .bind(DAOConstants.TITLE_PLACEHOLDER, "%" + title + "%")
                .list();
    }


    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        Handle h = DBIUtil.getHandle(dbi);
        int offset = DAOUtils.calcOffset(pageSize, pageNum);
        return h.createQuery(SELECT_EVENT_FOR_DAY)
                .map(EVENT_MAPPER)
                .bind(DAOConstants.LIMIT_PLACEHOLDER, pageSize)
                .bind(DAOConstants.OFFSET_PLACEHOLDER, offset)
                .bind(DAOConstants.DAY_PLACEHOLDER, day)
                .list();
    }

    @Override
    public Event create(Event event) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(INSERT_EVENT)
                .bind(DAOConstants.TITLE_PLACEHOLDER, event.getTitle())
                .bind(DAOConstants.DAY_PLACEHOLDER, event.getDate())
                .bind("ticket_price", event.getTicketPrice());
        GeneratedKeys<Long> keys = stmt.executeAndReturnGeneratedKeys(LongMapper.FIRST);
        event.setId(keys.first());
        return event;
    }

    @Override
    public Event update(Event event) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(UPDATE_EVENT)
                .bind(DAOConstants.TITLE_PLACEHOLDER, event.getTitle())
                .bind(DAOConstants.DAY_PLACEHOLDER, event.getDate())
                .bind(DAOConstants.TICKET_PRICE_PLACEHOLDER, event.getTicketPrice())
                .bind(DAOConstants.ID_PLACEHOLDER, event.getId());
        stmt.execute();
        return event;
    }

    @Override
    public boolean delete(long eventId) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(DELETE_EVENT)
                .bind(DAOConstants.ID_PLACEHOLDER, eventId);
        int deletedRows = stmt.execute();
        return deletedRows == 1;
    }
}
