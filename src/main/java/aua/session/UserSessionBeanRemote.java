package aua.session;

import aua.utils.ResponseStatus;


public interface UserSessionBeanRemote {
    ResponseStatus login(String email, String password);
    ResponseStatus register(String email, String password);
}
