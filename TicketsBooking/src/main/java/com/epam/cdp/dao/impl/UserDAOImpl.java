package com.epam.cdp.dao.impl;

import com.epam.cdp.dao.DAOConstants;
import com.epam.cdp.dao.DAOUtils;
import com.epam.cdp.dao.UserDAO;
import com.epam.cdp.dao.impl.mappers.UserDetailsMapper;
import com.epam.cdp.dao.impl.mappers.UserMapper;
import com.epam.cdp.model.User;
import org.skife.jdbi.v2.GeneratedKeys;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.Update;
import org.skife.jdbi.v2.spring.DBIUtil;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.LongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final ResultSetMapper<User> USER_MAPPER = new UserMapper();
    private final ResultSetMapper<UserDetails> USER_DETAILS_MAPPER = new UserDetailsMapper();
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=:id";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = :email ";
    private static final String SELECT_USERS_BY_NAME = "SELECT * FROM users WHERE name LIKE :name " + DAOConstants.LIMIT_OFFSET;
    private static final String INSERT_USER = "INSERT INTO users VALUES(default,:name,:email)";
    private static final String UPDATE_USER = "UPDATE users SET name=:name, email=:email WHERE id=:id";
    private static final String DELETE_USER = "DELETE FROM users WHERE id=:id";
    private static final String SELECT_USER_DETAILS_BY_EMAIL = "SELECT * FROM users AS a INNER JOIN users_details AS b ON a.id=b.user_id WHERE email = :email";
    @Autowired
    private
    IDBI dbi;

    @Override
    public User getUserById(long userId) {
        Handle h = DBIUtil.getHandle(dbi);
        return h.createQuery(SELECT_USER_BY_ID)
                .map(USER_MAPPER)
                .bind(DAOConstants.ID_PLACEHOLDER, userId)
                .first();
    }

    @Override
    public User getUserByEmail(String email) {
        Handle h = DBIUtil.getHandle(dbi);
        return h.createQuery(SELECT_USER_BY_EMAIL)
                .map(USER_MAPPER)
                .bind(DAOConstants.EMAIL_PLACEHOLDER, email)
                .first();
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        Handle h = DBIUtil.getHandle(dbi);
        int offset = DAOUtils.calcOffset(pageSize, pageNum);
        return h.createQuery(SELECT_USERS_BY_NAME)
                .map(USER_MAPPER)
                .bind(DAOConstants.LIMIT_PLACEHOLDER, pageSize)
                .bind(DAOConstants.OFFSET_PLACEHOLDER, offset)
                .bind(DAOConstants.NAME_PLACEHOLDER, "%" + name + "%")
                .list();
    }

    @Override
    public User create(User user) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(INSERT_USER)
                .bind(DAOConstants.NAME_PLACEHOLDER, user.getName())
                .bind(DAOConstants.EMAIL_PLACEHOLDER, user.getEmail());
        GeneratedKeys<Long> keys = stmt.executeAndReturnGeneratedKeys(LongMapper.FIRST);
        user.setId(keys.first());
        return user;
    }

    @Override
    public User update(User user) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(UPDATE_USER)
                .bind(DAOConstants.NAME_PLACEHOLDER, user.getName())
                .bind(DAOConstants.EMAIL_PLACEHOLDER, user.getEmail())
                .bind(DAOConstants.ID_PLACEHOLDER, user.getId());
        stmt.execute();
        return user;
    }

    @Override
    public boolean delete(long userId) {
        Handle h = DBIUtil.getHandle(dbi);
        Update stmt = h.createStatement(DELETE_USER)
                .bind(DAOConstants.ID_PLACEHOLDER, userId);
        int deletedRows = stmt.execute();
        return deletedRows == 1;
    }

    @Override
    public UserDetails getUserDetailsByEmail(String email) {
        Handle h = DBIUtil.getHandle(dbi);
        return h.createQuery(SELECT_USER_DETAILS_BY_EMAIL)
                .map(USER_DETAILS_MAPPER)
                .bind(DAOConstants.EMAIL_PLACEHOLDER, email)
                .first();
    }
}
