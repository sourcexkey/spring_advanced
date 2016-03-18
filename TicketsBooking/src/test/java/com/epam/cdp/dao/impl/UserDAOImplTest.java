package com.epam.cdp.dao.impl;

import com.epam.cdp.dao.UserDAO;
import com.epam.cdp.model.User;
import com.epam.cdp.model.impl.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
public class UserDAOImplTest {
    private static final long SOME_ID = 5;

    private static final String USER_NAME = "user name";
    private static final String USER_EMAIL = "user email";
    private static final long NON_EXISTS_ID = Integer.MAX_VALUE;
    private static final int USERS_COUNT = 9;
    private static final int PAGE_SIZE = 5;
    private static final long ID_FOR_CREATE = USERS_COUNT + 1;
    private static final int PAGE_NUM = 1;
    private List<User> testUsers;
    private User user;
    private Random rand = new Random();

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testGetUserById() throws Exception {
        initUserById(SOME_ID);
        assertEquals(user, userDAO.getUserById(SOME_ID));
    }

    @Test
    public void testGetUserByIdNegative() throws Exception {
        assertEquals(user, userDAO.getUserById(NON_EXISTS_ID));
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        initUsers();
        long id = rand.nextInt(USERS_COUNT-1)+1;
        assertEquals(new UserEntity(id, USER_NAME + id, USER_EMAIL + id), userDAO.getUserByEmail(USER_EMAIL + id));
    }

    @Test
    public void testGetUserByEmailNegative() throws Exception {
        initUsers();
        assertEquals(null, userDAO.getUserByEmail(USER_EMAIL + NON_EXISTS_ID));
    }

    @Test
    public void testGetUsersByName() throws Exception {
        initUsers();
        List<User> users = userDAO.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUM);
        assertTrue(testUsers.containsAll(users));
    }

    @Test
    public void testCreate() throws Exception {
        initUserById(ID_FOR_CREATE);
        User user = userDAO.create(this.user.clone());
        assertEquals(ID_FOR_CREATE, user.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        initUserById(SOME_ID);
        User updateUser = userDAO.update(user);
        assertEquals(user, updateUser);
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(userDAO.delete(SOME_ID));
    }

    @Test
    public void testDeleteNegative() throws Exception {
        assertFalse(userDAO.delete(NON_EXISTS_ID));
    }

    private void initUsers() {
        testUsers = new ArrayList<>();
        for (int i = 1; i <= USERS_COUNT; i++) {
            initUserById(i);
            testUsers.add(user);
        }
    }

    private void initUserById(long id) {
        user = new UserEntity(id, USER_NAME + id, USER_EMAIL + id);
    }

}