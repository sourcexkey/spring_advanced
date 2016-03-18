package com.epam.cdp.dao.impl.mappers;

import com.epam.cdp.dao.DAOConstants;
import com.epam.cdp.model.UserAccount;
import com.epam.cdp.model.impl.UserAccountEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountMapper implements ResultSetMapper<UserAccount> {
    @Override
    public UserAccount map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
        UserAccount userAccount = new UserAccountEntity();
        userAccount.setId(resultSet.getLong(DAOConstants.ID_PLACEHOLDER));
        userAccount.setUserId(resultSet.getLong(DAOConstants.USER_ID_PLACEHOLDER));
        userAccount.setCurrency(resultSet.getBigDecimal(DAOConstants.CURRENCY_PLACEHOLDER));
        return userAccount;
    }
}
