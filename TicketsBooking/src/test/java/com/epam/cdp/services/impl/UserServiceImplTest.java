package com.epam.cdp.services.impl;

import com.epam.cdp.dao.UserDAO;
import com.epam.cdp.model.impl.UserEntity;
import com.epam.cdp.services.UserService;
import com.epam.cdp.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;

import static org.mockito.Mockito.mock;

public class UserServiceImplTest {
    private static final String USER_DAO_FIELD_NAME = "userDAO";
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUM = 1;
    public static final Random rand = new Random();
    private static final String EMPTY_STR = "";
    private static final String NULL_STR = null;
    private static final String USER_NAME = "name";
    private UserService userService = new UserServiceImpl();
    private static final long SOME_ID = 5;

    @Mock
    private UserDAO userDAOMock = mock(UserDAO.class);


    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(userService, USER_DAO_FIELD_NAME, userDAOMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByEmptyEmail() throws Exception {
        userService.getUserByEmail(EMPTY_STR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByNullEmail() throws Exception {
        userService.getUserByEmail(EMPTY_STR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUsersByNameInvalidPageSize() throws Exception {
        userService.getUsersByName(USER_NAME, 0, PAGE_NUM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUsersByNameInvalidPageNum() throws Exception {
        userService.getUsersByName(USER_NAME, PAGE_SIZE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUser() throws Exception {
        userService.createUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullUser() throws Exception {
        userService.updateUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserWithNoSetId() throws Exception {
        userService.updateUser(new UserEntity());
    }
}