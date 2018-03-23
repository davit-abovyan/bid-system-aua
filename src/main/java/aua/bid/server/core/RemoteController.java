package aua.bid.server.core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    boolean makeBid(String bid) throws RemoteException;
    boolean login(StartServer startServer) throws RemoteException;
    void finaliseResult(StartServer startServer) throws RemoteException;
    void startAuction(StartServer startServer) throws RemoteException;
}
