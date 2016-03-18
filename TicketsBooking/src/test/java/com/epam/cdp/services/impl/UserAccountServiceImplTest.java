package com.epam.cdp.services.impl;

import com.epam.cdp.dao.UserAccountDAO;
import com.epam.cdp.model.impl.UserAccountEntity;
import com.epam.cdp.services.UserAccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;

import static org.mockito.Mockito.mock;

public class UserAccountServiceImplTest {
    private static final String USER_ACCOUNT_DAO_FIELD_NAME = "userAccountDAO";
    public static final Random rand = new Random();
    private UserAccountService userAccountService = new UserAccountServiceImpl();


    @Mock
    private UserAccountDAO userAccountDAO = mock(UserAccountDAO.class);


    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(userAccountService, USER_ACCOUNT_DAO_FIELD_NAME, userAccountDAO);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserAccount() throws Exception {
        userAccountService.createUserAccount(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullUserAccount() throws Exception {
        userAccountService.updateUserAccount(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserAccountWithNoSetId() throws Exception {
        userAccountService.updateUserAccount(new UserAccountEntity());
    }
}