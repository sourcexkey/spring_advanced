package com.epam.cdp.services.impl;

import com.epam.cdp.dao.UserDAO;
import com.epam.cdp.model.User;
import com.epam.cdp.model.impl.TicketEntity;
import com.epam.cdp.model.impl.UserEntity;
import com.epam.cdp.model.impl.Users;
import com.epam.cdp.services.UserService;
import com.epam.cdp.services.utils.ArgsCheckingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    @Qualifier("userMarshaller")
    private CastorMarshaller unmarshaller;
    @Autowired
    private TransactionTemplate tTemplate;

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

    @Override
    public void loadUsersFromFile(final InputStream is) {
        tTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                List<UserEntity> users;
                try {
                    Users unmarshall = (Users) unmarshaller.unmarshal(new StreamSource(is));
                    users = unmarshall.getUsers();
                } catch (IOException e) {
                    LOG.warn("IOException:", e);
                    return;
                }
                for (UserEntity u : users) {
                    userDAO.create(u);
                }
            }
        });
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
