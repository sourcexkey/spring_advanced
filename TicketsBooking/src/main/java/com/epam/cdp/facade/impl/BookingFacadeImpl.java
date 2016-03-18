package com.epam.cdp.facade.impl;

import com.epam.cdp.facade.BookingFacade;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.Ticket;
import com.epam.cdp.model.User;
import com.epam.cdp.model.UserAccount;
import com.epam.cdp.model.impl.DefaultUserEvent;
import com.epam.cdp.services.EventService;
import com.epam.cdp.services.TicketService;
import com.epam.cdp.services.UserAccountService;
import com.epam.cdp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class BookingFacadeImpl implements BookingFacade {
    private static final Logger LOG = LoggerFactory.getLogger(BookingFacadeImpl.class);

    private EventService eventService;
    private UserService userService;
    private TicketService ticketService;
    private UserAccountService userAccountService;

    @Autowired
    private DefaultUserEvent defaultUserEvent;

    public BookingFacadeImpl(EventService eventService, UserService userService, TicketService ticketService, UserAccountService userAccountService, DefaultUserEvent defaultUserEvent) {
        this.eventService = eventService;
        this.userService = userService;
        this.ticketService = ticketService;
        this.userAccountService = userAccountService;
        this.defaultUserEvent = defaultUserEvent;
    }

    @Override
    public Event getEventById(long eventId) {
        return eventService.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userService.deleteUser(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketService.cancelTicket(ticketId);
    }

    @Override
    public UserAccount getUserAccountById(long userAccountId) {
        return userAccountService.getUserAccountById(userAccountId);
    }

    @Override
    public UserAccount getUserAccountByUserId(long userId) {
        return userAccountService.getUserAccountByUserId(userId);
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return userAccountService.createUserAccount(userAccount);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        return userAccountService.updateUserAccount(userAccount);
    }

    @Override
    public boolean deleteUserAccount(long userAccountId) {
        return userAccountService.deleteUserAccount(userAccountId);
    }

    public void loadTicketsFromFile(InputStream is) {
        ticketService.loadTicketsFromFile(is);
    }

    @Override
    public void setDefaultUser(User defaultUser) {
        defaultUserEvent.setDefaultUser(defaultUser);
    }

    @Override
    public void setDefaultEvent(Event defaultEvent) {
        defaultUserEvent.setDefaultEvent(defaultEvent);
    }
}
