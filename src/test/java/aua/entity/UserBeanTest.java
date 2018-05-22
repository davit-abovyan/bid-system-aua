package aua.entity;

import aua.session.UserSessionBeanRemote;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import static org.junit.Assert.*;

public class UserBeanTest {
    @Inject
    UserBean user;

    @Produces
    @Mock
    UserSessionBeanRemote userSession;

    @Before
    public void setup() {
        System.out.println("Setup test environment");
        MockitoAnnotations.initMocks(this);
    }


    @org.junit.Test
    public void login() {

    }

    @org.junit.Test
    public void register() {
    }

    @After
    public void tearDown() {
        System.out.println("Setup test cleanup");
    }
}