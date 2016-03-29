package com.epam.cdp.dao.impl.mappers;

import com.epam.cdp.dao.DAOConstants;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UserDetailsMapper implements ResultSetMapper<UserDetails> {

    public static final String COMMA = ",";

    @Override
    public UserDetails map(int index, ResultSet resultSet, StatementContext ctx)
            throws SQLException {
        return new User(
                resultSet.getString(DAOConstants.EMAIL_PLACEHOLDER),
                resultSet.getString(DAOConstants.PASSWORD_PLACEHOLDER),
                resultSet.getBoolean(DAOConstants.ENABLED_PLACEHOLDER),
                resultSet.getBoolean(DAOConstants.ACCOUNT_NON_EXPIRED_PLACEHOLDER),
                resultSet.getBoolean(DAOConstants.CREDENTIALS_NON_EXPIRED_PLACEHOLDER),
                resultSet.getBoolean(DAOConstants.ACCOUNT_NON_LOCKED_PLACEHOLDER),
                Arrays.asList(resultSet.getString(DAOConstants.AUTHORITIES_PLACEHOLDER).split(COMMA))
                        .stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
