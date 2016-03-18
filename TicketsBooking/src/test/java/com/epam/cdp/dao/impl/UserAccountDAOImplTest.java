package com.epam.cdp.dao.impl;

import com.epam.cdp.dao.UserAccountDAO;
import com.epam.cdp.model.UserAccount;
import com.epam.cdp.model.impl.UserAccountEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
public class UserAccountDAOImplTest {
    private static final long SOME_ID = 5;

    private static final long NON_EXISTS_ID = Integer.MAX_VALUE;
    private static final int USER_ACCOUNT_COUNT = 9;
    private static final long ID_FOR_CREATE = USER_ACCOUNT_COUNT + 1;
    private static final int USERS_COUNT = 9;
    private UserAccount userAccount;
    private Random rand = new Random();

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Test
    public void testGetUserAccountById() throws Exception {
        initUserAccountById(SOME_ID);
        assertEquals(userAccount, userAccountDAO.getUserAccountById(SOME_ID));
    }

    @Test
    public void testGetUserByIdNegative() throws Exception {
        assertEquals(null, userAccountDAO.getUserAccountById(NON_EXISTS_ID));
    }

    @Test
    public void testGetUserAccountByUserId() throws Exception {
        initUserAccountById(SOME_ID);
        assertEquals(userAccount, userAccountDAO.getUserAccountByUserId(SOME_ID));
    }

    @Test
    public void testGetUserByUserIdNegative() throws Exception {
        assertEquals(null, userAccountDAO.getUserAccountByUserId(NON_EXISTS_ID));
    }

    @Test
    public void testCreate() throws Exception {
        initUserAccountById(ID_FOR_CREATE);
        userAccount.setUserId(1);
        UserAccount userAccount = userAccountDAO.createUserAccount((UserAccount) this.userAccount.clone());
        assertEquals(ID_FOR_CREATE, userAccount.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        initUserAccountById(SOME_ID);
        UserAccount updateUser = userAccountDAO.updateUserAccount(userAccount);
        assertEquals(userAccount, updateUser);
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(userAccountDAO.deleteUserAccount(SOME_ID));
    }

    @Test
    public void testDeleteNegative() throws Exception {
        assertFalse(userAccountDAO.deleteUserAccount(NON_EXISTS_ID));
    }

    private void initUserAccountById(long id) {
        userAccount = new UserAccountEntity(id, id, new BigDecimal("100.00").multiply(new BigDecimal(id)));
    }

}