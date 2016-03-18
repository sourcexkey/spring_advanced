package com.epam.cdp.dao.impl;

import com.epam.cdp.dao.DAOConstants;
import com.epam.cdp.dao.UserAccountDAO;
import com.epam.cdp.dao.impl.mappers.UserAccountMapper;
import com.epam.cdp.model.UserAccount;
import org.skife.jdbi.v2.GeneratedKeys;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.Update;
import org.skife.jdbi.v2.spring.DBIUtil;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.LongMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountDAOImpl implements UserAccountDAO {
    private final ResultSetMapper<UserAccount> USER__ACCOUNT_MAPPER = new UserAccountMapper();
    private static final String SELECT_USER_ACCOUNT_BY_ID = "SELECT * FROM user_accounts WHERE id=:id";
    private static final String SELECT_USER_ACCOUNT_BY_USER_ID = "SELECT * FROM user_accounts WHERE user_id=:user_id";
    private static final String INSERT_USER_ACCOUNT = "INSERT INTO user_accounts VALUES(default,:user_id,:currency)";
    private static final String UPDATE_USER_ACCOUNT = "UPDATE user_accounts SET user_id=:user_id, currency=:currency WHERE id=:id";
    private static final String DELETE_USER_ACCOUNT = "DELETE FROM user_accounts WHERE id=:id";
    @Autowired
    private
    IDBI dbi;

    @Override
    public UserAccount getUserAccountById(long userAccountId) {
        Handle h = DBIUtil.getHandle(dbi);
        return h.createQuery(SELECT_USER_ACCOUNT_BY_ID)
                .map(USER__ACCOUNT_MAPPER)
                .bind(DAOConstants.ID_PLACEHOLDER, userAccountId)
                .first();
    }

    @Override
    public UserAccount getUserAccountByUserId(long userId) {
        Handle h = DBIUtil.getHandle(dbi);
        return h.createQuery(SELECT_USER_ACCOUNT_BY_USER_ID)
                .map(USER__ACCOUNT_MAPPER)
                .bind(DAOConstants.USER_ID_PLACEHOLDER, userId)
                .first();
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(INSERT_USER_ACCOUNT)
                .bind(DAOConstants.USER_ID_PLACEHOLDER, userAccount.getUserId())
                .bind(DAOConstants.CURRENCY_PLACEHOLDER, userAccount.getCurrency());
        GeneratedKeys<Long> keys = stmt.executeAndReturnGeneratedKeys(LongMapper.FIRST);
        userAccount.setId(keys.first());
        return userAccount;
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(UPDATE_USER_ACCOUNT)
                .bind(DAOConstants.USER_ID_PLACEHOLDER, userAccount.getUserId())
                .bind(DAOConstants.CURRENCY_PLACEHOLDER, userAccount.getCurrency())
                .bind(DAOConstants.ID_PLACEHOLDER, userAccount.getId());
        stmt.execute();
        return userAccount;
    }

    @Override
    public boolean deleteUserAccount(long userAccountId) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(DELETE_USER_ACCOUNT)
                .bind(DAOConstants.ID_PLACEHOLDER, userAccountId);
        int deletedRows = stmt.execute();
        return deletedRows == 1;
    }
}
