package com.epam.cdp.services.impl;

import com.epam.cdp.dao.TicketDAO;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.impl.EventEntity;
import com.epam.cdp.model.User;
import com.epam.cdp.model.impl.UserEntity;
import com.epam.cdp.services.TicketService;
import com.epam.cdp.services.impl.TicketServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;

import static org.mockito.Mockito.mock;

public class TicketServiceImplTest {
    private static final String TICKET_DAO_FIELD_NAME = "ticketDAO";
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUM = 1;
    private static final Random rand = new Random();
    private TicketService ticketService = new TicketServiceImpl();
    private static final long SOME_ID = 5;

    @Mock
    private TicketDAO ticketDAOMock = mock(TicketDAO.class);

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(ticketService, TICKET_DAO_FIELD_NAME, ticketDAOMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookTicket() throws Exception {
        ticketService.bookTicket(SOME_ID, SOME_ID, rand.nextInt(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBookedTicketsNullUser() throws Exception {
        User user = null;
        ticketService.getBookedTickets(user, PAGE_SIZE, PAGE_NUM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBookedTicketsInvalidPageSize() throws Exception {
        ticketService.getBookedTickets(new UserEntity(), 0, PAGE_NUM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBookedTicketsInvalidPageNum() throws Exception {
        ticketService.getBookedTickets(new UserEntity(), PAGE_SIZE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBookedTicketsByNullEvent() throws Exception {
        Event event = null;
        ticketService.getBookedTickets(event, PAGE_SIZE, PAGE_NUM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBookedTicketsByEventInvalidPageSize() throws Exception {
        ticketService.getBookedTickets(new EventEntity(), 0, PAGE_NUM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBookedTicketsByEventInvalidPageNum() throws Exception {
        ticketService.getBookedTickets(new EventEntity(), PAGE_SIZE, 0);
    }
}