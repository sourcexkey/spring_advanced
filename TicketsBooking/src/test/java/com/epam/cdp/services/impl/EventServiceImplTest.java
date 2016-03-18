package com.epam.cdp.services.impl;

import com.epam.cdp.dao.EventDAO;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.impl.EventEntity;
import com.epam.cdp.services.EventService;
import com.epam.cdp.utils.PaginationUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceImplTest {
    private static final String EVENT_DAO_FIELD_NAME = "eventDAO";
    private static final int EVENTS_COUNT = 10;
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUM = 1;
    private EventService eventService = new EventServiceImpl();
    private static final long SOME_ID = 5;
    private static final String EVENT_TITLE = "event title";
    @Mock
    private EventDAO eventDAOMock = mock(EventDAO.class);
    private List<Event> testEvents;

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(eventService, EVENT_DAO_FIELD_NAME, eventDAOMock);
    }

    private void initEvents() {
        testEvents = new ArrayList<>();
        for (int i = 0; i < EVENTS_COUNT; i++) {
            Event event = new EventEntity(i, EVENT_TITLE + i, new Date(i),new BigDecimal(i*1000));
            testEvents.add(event);
        }
    }

    @Test
    public void testGetEventsByTitle() throws Exception {
        initEvents();
        when(eventDAOMock.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM)).thenReturn(PaginationUtils.getPaginationSubList(testEvents, PAGE_SIZE, PAGE_NUM));
        List<Event> eventsByTitle = eventService.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUM);
        assertTrue(eventsByTitle.size() <= PAGE_SIZE);
        assertTrue(testEvents.containsAll(eventsByTitle));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsByTitlePageSizeZero() throws Exception {
        eventService.getEventsByTitle(EVENT_TITLE, 0, PAGE_NUM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsByTitlePageNumLessThanOne() throws Exception {
        eventService.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, 0);
    }

    @Test
    public void testGetEventsForDay() throws Exception {
        initEvents();
        when(eventDAOMock.getEventsForDay(null, PAGE_SIZE, PAGE_NUM)).thenReturn(Collections.<Event>emptyList());
        assertTrue(eventService.getEventsForDay(null, PAGE_SIZE, PAGE_NUM).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsForDayPageSizeZero() throws Exception {
        eventService.getEventsForDay(null, 0, PAGE_SIZE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsForDayPageNumLessThanOne() throws Exception {
        eventService.getEventsForDay(null, PAGE_NUM, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullEvent() throws Exception {
        eventService.createEvent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullEvent() throws Exception {
        eventService.updateEvent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEventWithOutId() throws Exception {
        Event event = new EventEntity();
        eventService.updateEvent(event);
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Event event = new EventEntity(SOME_ID, EVENT_TITLE, new Date(SOME_ID),new BigDecimal(100));
        when(eventDAOMock.update(event)).thenReturn(event);
        assertEquals(event, eventService.updateEvent(event));
    }

}