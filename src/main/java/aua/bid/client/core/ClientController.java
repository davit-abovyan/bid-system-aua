package aua.bid.client.core;

import aua.bid.Remote.RemoteController;

import java.rmi.Naming;

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
