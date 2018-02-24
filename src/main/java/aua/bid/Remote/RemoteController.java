package aua.bid.Remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    void makeBid(int bidderNumber) throws RemoteException;
    boolean login() throws RemoteException;
    void finaliseResult(int auction) throws RemoteException;
    void startAuction() throws RemoteException;
    void createAdminRecord(String file) throws RemoteException;
}
