package com.epam.cdp.dao.impl.mappers;

import com.epam.cdp.dao.DAOConstants;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.impl.EventEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventMapper implements ResultSetMapper<Event> {
    @Override
    public Event map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
        Event event = new EventEntity();
        event.setId(resultSet.getLong(DAOConstants.ID_PLACEHOLDER));
        event.setTitle(resultSet.getString(DAOConstants.TITLE_PLACEHOLDER));
        event.setDate(resultSet.getDate(DAOConstants.DAY_PLACEHOLDER));
        event.setTicketPrice(resultSet.getBigDecimal(DAOConstants.TICKET_PRICE_PLACEHOLDER));
        return event;
    }
}