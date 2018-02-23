package aua.bid.client.core;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientController {

    public static void main(String args[]) {
        try {
            RemoteController controller = (RemoteController) Naming.lookup("rmi://" + args[0] + "/Controller");
            controller.startAuction();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
