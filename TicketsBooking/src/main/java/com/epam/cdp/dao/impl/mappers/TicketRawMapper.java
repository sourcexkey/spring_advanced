package com.epam.cdp.dao.impl.mappers;

import com.epam.cdp.dao.DAOConstants;
import com.epam.cdp.model.Ticket;
import com.epam.cdp.model.impl.TicketEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRawMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Ticket ticket = new TicketEntity();
        ticket.setId(resultSet.getLong(DAOConstants.ID_PLACEHOLDER));
        ticket.setEventId(resultSet.getLong(DAOConstants.EVENT_ID_PLACEHOLDER));
        ticket.setUserId(resultSet.getLong(DAOConstants.USER_ID_PLACEHOLDER));
        ticket.setCategory(Ticket.Category.valueOf(resultSet.getString(DAOConstants.CATEGORY_PLACEHOLDER)));
        ticket.setPlace(resultSet.getInt(DAOConstants.PLACE_PLACEHOLDER));
        return ticket;
    }
}
