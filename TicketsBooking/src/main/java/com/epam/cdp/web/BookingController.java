package com.epam.cdp.web;

import com.epam.cdp.facade.BookingFacade;
import com.epam.cdp.model.Event;
import com.epam.cdp.model.impl.EventEntity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BookingController {

    private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);
    public static final String URL_PATTERN__ADMIN_EVENT = "/event";
    private static final long DEFAULT_ID = 0L;
    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";
    public static final String DATE = "date";
    public static final String TITLE = "title";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    BookingFacade facade;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping(URL_PATTERN__ADMIN_EVENT)
    public String event() {
        return "/admin/event";
    }

    @RequestMapping(value = URL_PATTERN__ADMIN_EVENT, params = "eventId")
    public ModelAndView getEventById(@RequestParam("eventId") long eventId) {
        ModelAndView mav = new ModelAndView();
        Event event = facade.getEventById(eventId);
        if (event == null) {
            mav.setViewName("redirect:/admin/result");
            return mav;
        }
        mav.setViewName("/admin/event");
        mav.addObject("event", event);
        return mav;
    }

    @RequestMapping(value = URL_PATTERN__ADMIN_EVENT, params = "action=Create", method = RequestMethod.POST)
    public String eventCreate(@RequestParam(value = TITLE, required = false) String title,
                              @RequestParam(value = DATE, required = false) String date,
                              @RequestParam(value = "ticketPrice", required = false) BigDecimal ticketPrice)
            throws ParseException {
        Event event = getEvent(DEFAULT_ID, title, date, ticketPrice);
        facade.createEvent(event);
        return "/admin/result/EventCreated";
    }

    @RequestMapping(value = URL_PATTERN__ADMIN_EVENT, params = "action=Update", method = RequestMethod.POST)
    public String eventUpdate(@RequestParam(value = "id", required = false) Long id,
                              @RequestParam(value = TITLE, required = false) String title,
                              @RequestParam(value = DATE, required = false) String date,
                              @RequestParam(value = "ticketPrice", required = false) BigDecimal ticketPrice)
            throws ParseException {
        Event event = getEvent(id, title, date, ticketPrice);
        facade.updateEvent(event);
        return "/admin/result/updated";
    }

    @RequestMapping(value = URL_PATTERN__ADMIN_EVENT, params = "action=Delete", method = RequestMethod.POST)
    public String eventDelete(@RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            return "";//todo error
        }
        facade.deleteEvent(id);
        return "/admin/result/delete";
    }

    private Event getEvent(Long id, String title, String date, BigDecimal ticketPrice)
            throws ParseException {

        return new EventEntity(id != null ? id : DEFAULT_ID, title, getDate(date), ticketPrice);
    }

    private Date getDate(String dateValue) throws ParseException {
        return StringUtils.isEmpty(dateValue) ? null : dateFormat.parse(dateValue);
    }

    @RequestMapping("/events")
    public String events() {
        return "/view/events";
    }

    @RequestMapping(value = "/events", params = "action=Title")
    public ModelAndView getEventsByTitle(
            @RequestParam(value = TITLE, required = false) String title,
            @RequestParam(value = PAGE_NUM, required = false) Integer pageNum,
            @RequestParam(value = PAGE_SIZE, required = false) Integer pageSize) {
        ModelAndView mav = new ModelAndView("/view/events");
        mav.addObject("events", facade.getEventsByTitle(title, pageSize, pageNum));
        return mav;
    }

    @RequestMapping(value = "/events", params = "action=Date")
    public ModelAndView getEventsByDate(@RequestParam(value = DATE, required = false) String date,
                                        @RequestParam(value = PAGE_NUM, required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = PAGE_SIZE, required = false, defaultValue = "10") Integer pageSize)
            throws ParseException {
        ModelAndView mav = new ModelAndView("/view/events");
        mav.addObject("events", facade.getEventsForDay(getDate(date), pageSize, pageNum));
        return mav;
    }
}
