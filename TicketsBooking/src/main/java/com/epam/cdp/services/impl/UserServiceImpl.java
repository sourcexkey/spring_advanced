package com.epam.cdp.services.impl;

import com.epam.cdp.dao.UserDAO;
import com.epam.cdp.model.User;
import com.epam.cdp.services.UserService;
import com.epam.cdp.services.utils.ArgsCheckingUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public User getUserById(long userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        ArgsCheckingUtils.checkIsEmptyString(email);
        return userDAO.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        ArgsCheckingUtils.checkPageSize(pageSize);
        ArgsCheckingUtils.checkPageNum(pageNum);
        return userDAO.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        ArgsCheckingUtils.checkIsNull(user);
        return userDAO.create(user);
    }

    @Override
    public User updateUser(User user) {
        ArgsCheckingUtils.checkIsNull(user);
        ArgsCheckingUtils.checkIsIdSet(user);
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userDAO.delete(userId);
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
