package aua.session;

import aua.utils.DbManager;
import aua.utils.ResponseStatus;

import javax.ejb.Stateless;

@Stateless(name = "UserSessionEJB")
public class UserSessionBean implements UserSessionBeanRemote {

    public UserSessionBean() {
    }

    @Override
    public ResponseStatus login(String email, String password) {
        int isUserRegistered;
        try {
            isUserRegistered = DbManager.getInstance().isUserExist(email, password);
            if (isUserRegistered != -1)
                return new ResponseStatus(200, "Welcome Back!");
            else
                return new ResponseStatus(401, "Invalid Email or Password");
        } catch (Exception e) {
            return new ResponseStatus(500, "Barev Gag.");
        }
    }


    @Override
    public ResponseStatus register(String email, String password) {
        boolean emailExists;
        try {
            emailExists = DbManager.getInstance().isEmailExist(email);
            if (!emailExists) {
                boolean addToDb = DbManager.getInstance().addUser(email, password);
                if (addToDb) {
                    return new ResponseStatus(200, "You Have Been Successfully Registered In Auction System.");
                } else {
                    return new ResponseStatus(501, "Connection error. Please Try Again.");
                }
            } else
                return new ResponseStatus(401, "A user with this email already exists.");
        } catch (Exception e) {
            return new ResponseStatus(500, "Connection error. Please try again.");
        }
    }
}
