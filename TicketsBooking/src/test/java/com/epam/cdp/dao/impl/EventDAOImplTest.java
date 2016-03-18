package com.epam.cdp.dao.impl;

import com.epam.cdp.dao.EventDAO;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.impl.EventEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
public class EventDAOImplTest {
    private static final long SOME_ID = 5;
    private static final String EVENT_TITLE = "event title";
    private static final long NON_EXISTS_ID = Integer.MAX_VALUE;
    private static final int EVENTS_COUNT = 9;
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUM = 1;
    private static final String SOME_DATE = "2015-05-1";
    private static final long ID_FOR_CREATE = EVENTS_COUNT + 1;
    private Event event;
    private List<Event> testEvents;
    private Random rand = new Random();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private EventDAO eventDAO;


    private void initEventById(long id) throws ParseException {
        event = new EventEntity(id, EVENT_TITLE + id, dateFormat.parse(SOME_DATE + id), new BigDecimal("10.00").multiply(new BigDecimal(id)));
    }

    private void initEvents() throws ParseException {
        testEvents = new ArrayList<>();
        for (int i = 1; i <= EVENTS_COUNT; i++) {
            initEventById(i);
            testEvents.add(event);
        }
    }
    @Test
    public void testGetEventById() throws Exception {
        initEventById(SOME_ID);
        assertEquals(event, eventDAO.getEventById(SOME_ID));
    }

    @Test
    public void testGetEventByIdNegative() throws Exception {
        assertNull(eventDAO.getEventById(NON_EXISTS_ID));
    }

    @Test
    public void testGetEventsByTitle() throws Exception {
        initEvents();
        List<Event> events = eventDAO.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM);
        assertEquals(PAGE_SIZE, events.size());
        assertTrue(testEvents.containsAll(events));
    }
    @Test
    public void testGetEventsByTitleNegative() throws Exception {
        List<Event> events = eventDAO.getEventsByTitle(EVENT_TITLE + NON_EXISTS_ID, PAGE_SIZE, PAGE_NUM);
        assertTrue(events.isEmpty());
    }

    @Test
    public void testGetEventsForDay() throws Exception {
        initEvents();
        List<Event> events = eventDAO.getEventsForDay(dateFormat.parse(SOME_DATE + (rand.nextInt(EVENTS_COUNT-1)+1)), PAGE_SIZE, PAGE_NUM);
        assertTrue(testEvents.containsAll(events));
    }

    @Test
    public void testGetEventsForDayNegative() throws Exception {
        initEvents();
        List<Event> events = eventDAO.getEventsForDay(new Date(), PAGE_SIZE, PAGE_NUM);
        assertTrue(events.isEmpty());
    }

    @Test
    public void testCreate() throws Exception {
        initEventById(ID_FOR_CREATE);
        Event event = eventDAO.create(this.event.clone());
        this.event.setId(ID_FOR_CREATE);
        assertEquals(this.event, event);
    }

    @Test
    public void testUpdate() throws Exception {
        initEventById(SOME_ID);
        Event updateEvent = eventDAO.update(event);
        assertEquals(event, updateEvent);
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(eventDAO.delete(EVENTS_COUNT));
    }

    @Test
    public void testDeleteNegative() throws Exception {
        assertFalse(eventDAO.delete(NON_EXISTS_ID));
    }
}