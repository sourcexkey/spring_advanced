package com.epam.cdp.dao.impl;

import com.epam.cdp.dao.DAOConstants;
import com.epam.cdp.dao.DAOUtils;
import com.epam.cdp.dao.DefaultEventUserSQLPS;
import com.epam.cdp.dao.TicketDAO;
import com.epam.cdp.dao.impl.mappers.TicketRawMapper;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.Ticket;
import com.epam.cdp.model.User;
import com.epam.cdp.model.impl.DefaultUserEvent;
import com.epam.cdp.model.impl.TicketEntity;
import org.skife.jdbi.v2.GeneratedKeys;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.Update;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.spring.DBIUtil;
import org.skife.jdbi.v2.util.LongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class TicketDAOImpl implements TicketDAO {
    private static final Logger LOG = LoggerFactory.getLogger(TicketDAOImpl.class);

    private static final String SELECT_TICKETS_BY_USER = "SELECT t.id,t.event_id,t.user_id,t.category,t.place,e.day FROM tickets AS t INNER JOIN events AS e ON t.event_id=e.id WHERE t.user_id = :user_id ORDER BY e.day DESC " + DAOConstants.LIMIT_OFFSET;
    private static final String SELECT_TICKETS_BY_EVENT = "SELECT t.id,t.event_id,t.user_id,t.category,t.place,u.email FROM tickets AS t INNER JOIN users AS u ON t.user_id=u.id  WHERE t.event_id = :event_id ORDER BY u.email ASC " + DAOConstants.LIMIT_OFFSET;
    private static final String INSERT_TICKET = "INSERT INTO tickets VALUES(default,:event_id,:user_id,:category,:place)";
    private static final String DELETE_TICKET = "DELETE FROM tickets WHERE id=:id";

    @Autowired
    private IDBI dbi;

    @Autowired
    private DefaultUserEvent defaultUserEvent;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setDbi(IDBI dbi) {
        this.dbi = dbi;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket ticket = new TicketEntity();
        ticket.setUserId(userId);
        ticket.setEventId(eventId);
        ticket.setPlace(place);
        ticket.setCategory(category);

        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(INSERT_TICKET)
                .bind(DAOConstants.USER_ID_PLACEHOLDER, userId)
                .bind(DAOConstants.EVENT_ID_PLACEHOLDER, eventId)
                .bind(DAOConstants.CATEGORY_PLACEHOLDER, category)
                .bind(DAOConstants.PLACE_PLACEHOLDER, place);
        GeneratedKeys<Long> keys;
        try {
            keys = stmt.executeAndReturnGeneratedKeys(LongMapper.FIRST);
        } catch (UnableToExecuteStatementException e) {
            String message = String.format("Ticket has already been booked. %s", ticket);
            LOG.warn(message);
            throw new IllegalStateException(message);
        }
        if (keys != null) {
            ticket.setId(keys.first());
        }
        return ticket;
    }
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        int offset = DAOUtils.calcOffset(pageSize, pageNum);
        SqlParameterSource parameters = new DefaultEventUserSQLPS(defaultUserEvent)
                .addValue(DAOConstants.USER_ID_PLACEHOLDER, user.getId())
                .addValue(DAOConstants.LIMIT_PLACEHOLDER, pageSize)
                .addValue(DAOConstants.OFFSET_PLACEHOLDER, offset);
        return jdbcTemplate.query(SELECT_TICKETS_BY_USER, parameters, new TicketRawMapper());
    }
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        int offset = DAOUtils.calcOffset(pageSize, pageNum);
        SqlParameterSource parameters = new DefaultEventUserSQLPS(defaultUserEvent)
                .addValue(DAOConstants.EVENT_ID_PLACEHOLDER, event.getId())
                .addValue(DAOConstants.LIMIT_PLACEHOLDER, pageSize)
                .addValue(DAOConstants.OFFSET_PLACEHOLDER, offset);
        return jdbcTemplate.query(SELECT_TICKETS_BY_EVENT, parameters, new TicketRawMapper());
    }

    public boolean cancelTicket(long ticketId) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(DELETE_TICKET)
                .bind(DAOConstants.ID_PLACEHOLDER, ticketId);
        int deletedRows = stmt.execute();
        return deletedRows == 1;
    }

}

