package com.epam.cdp.model;

import java.math.BigDecimal;

public interface UserAccount extends Entity {
    long getUserId();

    void setUserId(long userId);

    BigDecimal getCurrency();

    void setCurrency(BigDecimal currency);
}
