package com.epam.cdp.dao.impl;

import com.epam.cdp.dao.TicketDAO;
import com.epam.cdp.model.Ticket;
import com.epam.cdp.model.impl.TicketEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
public class TicketDAOImplTest {
    private static final long NON_EXISTS_ID = Integer.MAX_VALUE;
    private static final int EVENTS_COUNT = 9;
    private static final int USERS_COUNT = 9;
    private static final int TICKETS_COUNT = 5;
    private Ticket ticket;
    private Random rand = new Random();
    private static final long SOME_ID = 5;
    private static final long NEW_ID = TICKETS_COUNT + 1;

    @Autowired
    private TicketDAO ticketDAO;

    @Test
    public void testBookTicket() throws Exception {
        ticket = new TicketEntity();
        ticket.setCategory(Ticket.Category.BAR);
        ticket.setEventId(rand.nextInt(EVENTS_COUNT-1)+1);
        ticket.setUserId(rand.nextInt(USERS_COUNT-1)+1);
        ticket.setPlace(rand.nextInt());
        ticket.setId(NEW_ID);
        assertEquals(ticket, ticketDAO.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory()));
    }

    @Test
    public void testCancelTicket() throws Exception {
        assertTrue(ticketDAO.cancelTicket(SOME_ID));
    }
    @Test
    public void testCancelTicketNegative() throws Exception {
        assertFalse(ticketDAO.cancelTicket(NON_EXISTS_ID));
    }
}