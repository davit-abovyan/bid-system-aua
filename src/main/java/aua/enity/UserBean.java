package aua.enity;

import aua.session.UserSessionBeanRemote;
import aua.utils.ResponseStatus;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;

@ManagedBean(name = "user")
@SessionScoped
public class UserBean {
    private int Id;
    private String email;
    private String password;

    private String responseMessage;
    private boolean isLoggedIn;
    private double accountBalance;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean loginStatus) {
        this.isLoggedIn = loginStatus;
    }

    public void logOut() { setIsLoggedIn(false); }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }


    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMsg) {
        this.responseMessage = responseMsg;
    }

    @EJB
    UserSessionBeanRemote user;

    Context context = null;

    public String login() {
        if (email != null && password != null) {
            ResponseStatus isRegisteredUser = user.login(email, password);
            if (isRegisteredUser.getStatus() == 200) {
                setIsLoggedIn(true);
                setResponseMessage("Hurrray");
                accountBalance = 0;
                return "Success";
            } else if (isRegisteredUser.getStatus() == 401) {
                setResponseMessage(isRegisteredUser.getMessage());
                return "Failed";
            } else if (isRegisteredUser.getStatus() == 500) {
                setResponseMessage(isRegisteredUser.getMessage());
                return "Failed";
            } else
                return "Failed";
        } else {
            setResponseMessage("Please Fill All Fields");
            return "Failed";
        }
    }

    public String register() {
        if (!email.trim().equals("") && !password.trim().equals("")) {
            ResponseStatus register = user.register(email, password);
            if (register.getStatus() == 200) {
                setResponseMessage(register.getMessage());
                System.out.println(register.getMessage());
                return "Success";
            } else if (register.getStatus() == 401) {
                setResponseMessage(register.getMessage());
                return "Fail";
            } else if (register.getStatus() == 500) {
                setResponseMessage(register.getMessage());
                return "Fail";
            } else if (register.getStatus() == 501) {
                setResponseMessage(register.getMessage());
                return "Fail";
            } else
                return "Fail";
        } else {
            setResponseMessage("Passwords do not match.");
            return "Fail";
        }
    }


}
