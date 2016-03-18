package com.epam.cdp.web;

import com.epam.cdp.facade.BookingFacade;
import com.epam.cdp.model.Ticket;
import com.epam.cdp.model.impl.UserEntity;
import com.epam.cdp.web.view.pdf.CustomPdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping
public class TicketsPDFController {

    @Autowired
    BookingFacade facade;

    @RequestMapping(value = "/bookedTickets",headers = "accept=application/pdf")
    public ModelAndView getEventsByTitle(@RequestParam(value = "userId") Long id,
                                 @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                 HttpServletResponse response,
                                 HttpServletRequest request, ModelMap model) {

        List<Ticket> tickets = facade.getBookedTickets(new UserEntity(id, null, null), pageSize, pageNum);

        return new ModelAndView(new CustomPdfView(), "tickets", tickets);
    }
}

