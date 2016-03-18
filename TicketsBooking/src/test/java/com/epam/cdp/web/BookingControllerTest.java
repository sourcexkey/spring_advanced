package com.epam.cdp.web;

import com.epam.cdp.model.Event;
import com.epam.cdp.model.impl.EventEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:dispatcherServlet-servlet.xml"})

public class BookingControllerTest {
    @Autowired
    WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testEvent() throws Exception {
        mockMvc.perform(get(BookingController.URL_PATTERN__ADMIN_EVENT))
                .andExpect(view().name("/admin/event"));
    }

    @Test
    public void testGetEventById() throws Exception {
        mockMvc.perform(get(BookingController.URL_PATTERN__ADMIN_EVENT))
                .andExpect(view().name("/admin/event"))
                .andReturn()
                .getModelAndView()
                .addObject("event", new EventEntity());
    }

    @Test
    public void testEventCreate() throws Exception {
        mockMvc.perform(post(BookingController.URL_PATTERN__ADMIN_EVENT).param("action", "Create"))
                .andExpect(view().name("/admin/result/EventCreated"));
    }

    @Test
    public void testGetEventsByTitle() throws Exception {
        mockMvc.perform(get("/events")
                .param("action", "Title")
                .param("title", "title")
                .param(BookingController.PAGE_NUM, "1")
                .param(BookingController.PAGE_SIZE, "10"))
                .andExpect(view().name("/view/events"))
                .andReturn()
                .getModelAndView()
                .addObject("events", new ArrayList<Event>());
    }
}