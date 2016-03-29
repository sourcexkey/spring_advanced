package com.epam.cdp.facade.impl;

import com.epam.cdp.facade.BookingFacade;
import com.epam.cdp.model.Entity;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.Ticket;
import com.epam.cdp.model.User;
import com.epam.cdp.model.UserAccount;
import com.epam.cdp.model.impl.EventEntity;
import com.epam.cdp.model.impl.UserAccountEntity;
import com.epam.cdp.model.impl.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
public class BookingFacadeImplTest {
    private static final long SOME_ID = 0;
    public static final String USER_EMAIL = "user email";
    public static final String USER_NAME = "user name";
    public static final String EVENT_TITLE = "event title";
    private static final int SOME_PLACE = 1;
    private static final int SOME_BOOKED_PLACE = 1;
    private static final int EXISTS_EVENT_ID = 5;
    private static final BigDecimal TICKET_PRICE = new BigDecimal(100);
    private static final BigDecimal UA_CURRENCY = new BigDecimal(1000);
    private static final BigDecimal NOT_ENOUGH_UA_CURRENCY = new BigDecimal(99);
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_NUM = 1;
    public static final int USER_ID = 1;


    @Autowired
    private BookingFacade bookingFacade;
    private User user;
    private UserAccount userAccount;
    private Map<String, Entity> storageMap;
    private Event event;
    private Ticket ticket;

    @Test
    public void testCreateUserEventTicketCancelTicket() {
        createUser();
        createUserAccount(UA_CURRENCY);
        createEvent(TICKET_PRICE);
        bookTicket(SOME_PLACE);
        cancelTicket();
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateUserBookBookedTicket() {
        createUser();
        createUserAccount(UA_CURRENCY);
        event = new EventEntity();
        event.setId(EXISTS_EVENT_ID);
        bookTicket(SOME_BOOKED_PLACE);
        bookTicket(SOME_BOOKED_PLACE);
    }

    @Test
    public void testCreateUserBookTicketWhenNotEnoughCurrency() {
        createUser();
        createUserAccount(NOT_ENOUGH_UA_CURRENCY);
        createEvent(TICKET_PRICE);
        bookToExpensiveTicket();
    }

    @Test
    public void getBookedTicketsByUser() {
        bookingFacade.setDefaultUser(null);
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(new UserEntity(USER_ID, USER_NAME, USER_EMAIL), PAGE_SIZE, PAGE_NUM);
        for (Ticket t : bookedTickets) {
            assertEquals(USER_ID, t.getUserId());
        }
    }

    private void cancelTicket() {
        assertTrue(bookingFacade.cancelTicket(ticket.getId()));
    }

    private void bookTicket(int place) {
        ticket = bookingFacade.bookTicket(user.getId(), event.getId(), place, Ticket.Category.BAR);
        assertEquals(user.getId(), ticket.getUserId());
        assertEquals(event.getId(), ticket.getEventId());
        assertEquals(place, ticket.getPlace());
        assertEquals(Ticket.Category.BAR, ticket.getCategory());
    }

    private void bookToExpensiveTicket() {
        assertNull(bookingFacade.bookTicket(user.getId(), event.getId(), SOME_PLACE, Ticket.Category.BAR));
    }

    private void createEvent(BigDecimal ticketPrice) {
        this.event = new EventEntity();
        this.event.setTitle(EVENT_TITLE);
        this.event.setDate(new Date());
        this.event.setTicketPrice(ticketPrice);
        Event event = bookingFacade.createEvent(this.event);
        this.event.setId(event.getId());
        assertEquals(this.event, event);
    }

    private void createUser() {
        this.user = new UserEntity();
        this.user.setName(USER_NAME);
        this.user.setEmail(USER_EMAIL);
        User user = bookingFacade.createUser(this.user);
        this.user.setId(user.getId());
        assertEquals(this.user, user);
    }

    private void createUserAccount(BigDecimal uaCurrency) {
        this.userAccount = new UserAccountEntity();
        this.userAccount.setUserId(user.getId());
        this.userAccount.setCurrency(uaCurrency);
        UserAccount userAccount = bookingFacade.createUserAccount(this.userAccount);
        this.userAccount.setId(userAccount.getId());
        assertEquals(this.userAccount, userAccount);
    }
}