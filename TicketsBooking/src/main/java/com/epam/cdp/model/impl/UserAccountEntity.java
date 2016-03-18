package com.epam.cdp.model.impl;

import com.epam.cdp.model.UserAccount;

import java.math.BigDecimal;

public class UserAccountEntity implements UserAccount {
    private long id;
    private long userId;
    private BigDecimal currency;

    public UserAccountEntity() {
    }

    public UserAccountEntity(long id, long userId, BigDecimal currency) {
        this.id = id;
        this.userId = userId;
        this.currency = currency;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public BigDecimal getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountEntity that = (UserAccountEntity) o;

        if (id != that.id) return false;
        return userId == that.userId && !(currency != null ? !currency.equals(that.currency) : that.currency != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public UserAccount clone() {
        UserAccountEntity newAccountEntity = new UserAccountEntity();
        newAccountEntity.id = this.id;
        newAccountEntity.userId = this.userId;
        newAccountEntity.currency = this.currency;
        return newAccountEntity;
    }

    @Override
    public String toString() {
        return "UserAccountEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", currency=" + currency +
                '}';
    }
}
