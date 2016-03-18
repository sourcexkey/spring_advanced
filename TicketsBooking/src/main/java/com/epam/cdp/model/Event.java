package com.epam.cdp.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by maksym_govorischev.
 */
public interface Event extends Entity {

    String getTitle();

    void setTitle(String title);

    Date getDate();

    void setDate(Date date);

    BigDecimal getTicketPrice();

    void setTicketPrice(BigDecimal ticketPrice);

    Event clone();
}
