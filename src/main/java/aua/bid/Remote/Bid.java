package aua.bid.Remote;

import aua.bid.Remote.Bidder;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Bid extends UnicastRemoteObject implements Bidder, Serializable{

    private String auctionNumber;
    private String email;
    private String price;

    public Bid() throws RemoteException{}

    public Bid(String auctionNumber, String email, String price) throws RemoteException {
        this.auctionNumber = auctionNumber;
        this.email = email;
        this.price = price;
    }

    public String getAuctionNumber() {
        return auctionNumber;
    }

    public void setAuctionNumber(String auctionNumber) {
        this.auctionNumber = auctionNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
