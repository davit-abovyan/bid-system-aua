package aua.bid.Remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    void makeBid(Bid bid) throws RemoteException;
    boolean login() throws RemoteException;
    void finaliseResult(int auction) throws RemoteException;
    void startAuction() throws RemoteException;
    void createAdminRecord(String file) throws RemoteException;
}
