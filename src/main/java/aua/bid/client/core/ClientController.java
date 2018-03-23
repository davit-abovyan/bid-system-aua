package aua.bid.client.core;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientController {

    public static void main(String args[]){
        try{
            aua.bid.server.core.RemoteController controller=(aua.bid.server.core.RemoteController) Naming.lookup("rmi://127.0.0.1/Controller");
//            controller.startAuction();
        }catch(Exception e){System.out.println(e);}
    }
}
