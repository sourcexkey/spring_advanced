package com.epam.cdp.services.impl;

import com.epam.cdp.dao.EventDAO;
import com.epam.cdp.dao.TicketDAO;
import com.epam.cdp.dao.UserAccountDAO;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.Ticket;
import com.epam.cdp.model.User;
import com.epam.cdp.model.UserAccount;
import com.epam.cdp.services.TicketService;
import com.epam.cdp.services.utils.ArgsCheckingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class TicketServiceImpl implements TicketService {
    private static final Logger LOG = LoggerFactory.getLogger(TicketServiceImpl.class);
    private String ticketsFileName;
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private UserAccountDAO userAccountDAO;
    @Autowired
    private EventDAO eventDAO;

    @Transactional
    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        ArgsCheckingUtils.checkIsNull(category);
        UserAccount userAccount = userAccountDAO.getUserAccountByUserId(userId);
        Event event = eventDAO.getEventById(eventId);
        BigDecimal currency = userAccount.getCurrency();
        BigDecimal ticketPrice = event.getTicketPrice();
        if (currency.compareTo(ticketPrice) < 0) {
            return null;
        }
        userAccount.setCurrency(currency.subtract(ticketPrice));
        userAccountDAO.updateUserAccount(userAccount);
        return ticketDAO.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        ArgsCheckingUtils.checkIsNull(user);
        ArgsCheckingUtils.checkPageSize(pageSize);
        ArgsCheckingUtils.checkPageNum(pageNum);
        return ticketDAO.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        ArgsCheckingUtils.checkIsNull(event);
        ArgsCheckingUtils.checkPageSize(pageSize);
        ArgsCheckingUtils.checkPageNum(pageNum);
        return ticketDAO.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketDAO.cancelTicket(ticketId);
    }

    public void setTicketsFileName(String ticketsFileName) {
        this.ticketsFileName = ticketsFileName;
    }
}
