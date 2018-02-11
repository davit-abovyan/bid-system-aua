package aua.bid.server.core;

import java.rmi.Naming;

public class TestController {
    // IMPORTANT: before running server, start rmiregistry from ... src/main/java folder
    public static void main(String[] args){
        try {
            RemoteController controller = new ServerController();
            Naming.rebind("Controller", controller);
            System.err.println("Server ready");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
