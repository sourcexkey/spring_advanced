package com.epam.cdp.services.impl;

import com.epam.cdp.dao.UserAccountDAO;
import com.epam.cdp.model.UserAccount;
import com.epam.cdp.services.UserAccountService;
import com.epam.cdp.services.utils.ArgsCheckingUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountDAO userAccountDAO;

    @Override
    public UserAccount getUserAccountById(long userAccountId) {
        return userAccountDAO.getUserAccountById(userAccountId);
    }

    @Override
    public UserAccount getUserAccountByUserId(long userId) {
        return userAccountDAO.getUserAccountByUserId(userId);
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        ArgsCheckingUtils.checkIsNull(userAccount);
        return userAccountDAO.createUserAccount(userAccount);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        ArgsCheckingUtils.checkIsNull(userAccount);
        ArgsCheckingUtils.checkIsIdSet(userAccount);
        return userAccountDAO.updateUserAccount(userAccount);
    }

    @Override
    public boolean deleteUserAccount(long userAccountId) {
        return userAccountDAO.deleteUserAccount(userAccountId);
    }

    public void setUserAccountDAO(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }
}
