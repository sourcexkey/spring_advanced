package com.epam.cdp.dao.impl.mappers;

import com.epam.cdp.dao.DAOConstants;
import com.epam.cdp.model.User;
import com.epam.cdp.model.impl.UserEntity;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
        User user = new UserEntity();
        user.setId(resultSet.getLong(DAOConstants.ID_PLACEHOLDER));
        user.setName(resultSet.getString(DAOConstants.NAME_PLACEHOLDER));
        user.setEmail(resultSet.getString(DAOConstants.EMAIL_PLACEHOLDER));
        return user;
    }
}
