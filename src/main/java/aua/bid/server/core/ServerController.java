package aua.bid.server.core;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerController extends UnicastRemoteObject implements RemoteController {

    private static int lastAuctionNumber = 0;
    private static ArrayList<String> runningAuctions = new ArrayList<>();

    protected ServerController() throws RemoteException {
        super();
    }

    public boolean makeBid(String bid)  throws RemoteException {
        String[] temp = bid.split("##");
        if(runningAuctions.contains(temp[0])) {
            try (FileWriter fw = new FileWriter(temp[0] + ".txt", true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(temp[1] + "##" + temp[2] + "\n");
                return true;
            } catch (IOException ex) {
                System.out.println("Something went wrong");
            }
        }
        return false;
    }

    public boolean login(StartServer startServer) throws RemoteException{
        final String adminFileName = "admin.txt";
        try (FileReader fr = new FileReader(adminFileName);
             BufferedReader br = new BufferedReader(fr)) {
            String email;
            if ((email = br.readLine()) != null) {
                    if (email.equals(startServer.admin.getText())) {
                        startServer.editAdminText(email);
                        return true;
                    }
                    else {
                        startServer.addToList("The email is wrong, please try again.");
                        return false;
                    }

            } else {
                startServer.addToList("Something went wrong, please restart application.");
            }
        } catch (FileNotFoundException e) {
            createAdminRecord(adminFileName, startServer);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void finaliseResult(StartServer startServer) throws RemoteException{

        try (FileReader fr = new FileReader(lastAuctionNumber+".txt");
             BufferedReader br = new BufferedReader(fr)) {
            String row;
            String email = "";
            int price = 0;
            while ((row = br.readLine()) != null) {
                String[] bid = row.split("##");
                if(Integer.valueOf(bid[1]) > price ){
                    price = Integer.valueOf(bid[1]);
                    email = bid[0];
                }
            }

            startServer.addToList(email+" won with price "+price);
            runningAuctions.remove(Integer.toString(lastAuctionNumber));

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void startAuction(StartServer startServer) throws RemoteException{
        ++lastAuctionNumber;
        startServer.addToList("Starting auction N"+lastAuctionNumber);
        runningAuctions.add(Integer.toString(lastAuctionNumber));
    }

    private void createAdminRecord(String file, StartServer startServer) throws RemoteException{
        startServer.addToList("System initialization - please input admin email: ");
        String email = startServer.admin.getText();
        while(true){
            if("".equals(email)){
                startServer.addToList("Please input email");
            } else if(!email.contains("@") || !email.contains(".")){
                startServer.addToList("Please input valid email");
            } else break;
        }
        String adminEmail = email;
        try(FileWriter fw = new FileWriter(file );
            BufferedWriter bw = new BufferedWriter(fw) ) {
            bw.write(adminEmail);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void allBids(StartServer startServer) throws RemoteException{

        try (FileReader fr = new FileReader(lastAuctionNumber+1+".txt");
             BufferedReader br = new BufferedReader(fr)) {
            String row;
            String email = "";
            int price = 0;
            while ((row = br.readLine()) != null) {
                String[] bid = row.split("##");
                startServer.addToList(bid[0]+" with price "+bid[1]);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public String bidResult(String auctionId) throws RemoteException {
        String result = "";
        if(runningAuctions.contains(auctionId)) result = "Auction is not closed yet";
        else {
            try (FileReader fr = new FileReader(auctionId + ".txt");
                 BufferedReader br = new BufferedReader(fr)) {
                String row;
                String email = "";
                int price = 0;
                while ((row = br.readLine()) != null) {
                    String[] bid = row.split("##");
                    if (Integer.valueOf(bid[1]) > price) {
                        price = Integer.valueOf(bid[1]);
                        email = bid[0];
                    }
                }
                result = email + " won with price " + price;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}