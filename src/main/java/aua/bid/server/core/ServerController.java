package aua.bid.server.core;

import aua.bid.Remote.Bid;
import aua.bid.Remote.Bidder;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerController extends UnicastRemoteObject implements RemoteController {

    private static int lastAuctionNumber = 0;
    private static ArrayList<Integer> runningAuctions = new ArrayList<>();

    protected ServerController() throws RemoteException {
        super();
    }

    public void makeBid(Bid bid)  throws RemoteException {
        if(runningAuctions.contains(bid.getAuctionNumber())) {
            try (FileWriter fw = new FileWriter(bid.getAuctionNumber() + ".txt", true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(bid.getEmail() + "##" + bid.getPrice() + "\n");
            } catch (IOException ex) {
                System.out.println("Something went wrong");
            }
        }
    }

    public boolean login(StartServer startServer) throws RemoteException{
        final String adminFileName = "admin.txt";
        try (FileReader fr = new FileReader(adminFileName);
             BufferedReader br = new BufferedReader(fr)) {
            String email;
            if ((email = br.readLine()) != null) {
                    if (email.equals(startServer.textArea.getText())) {
                        startServer.addToList("Admin: " + email);
                        return true;
                    }
                    else {
                        startServer.addToList("Wrong email.");
                        return false;
                    }

            } else {
                startServer.addToList("Something went wrong, please restart application.");
            }
        } catch (FileNotFoundException e) {
            createAdminRecord(adminFileName);
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

            System.out.println(email+" won with price "+price);

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void startAuction(StartServer startServer) throws RemoteException{
        ++lastAuctionNumber;
        startServer.addToList("Starting auction N"+lastAuctionNumber);
        runningAuctions.add(lastAuctionNumber);
    }

    public void createAdminRecord(String file) throws RemoteException{
        System.out.println("System initialization - please input admin email: ");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        while(true){
            if("".equals(email)){
                System.out.println("Please input email");
                email = scanner.nextLine();
            } else if(!email.contains("@") || !email.contains(".")){
                System.out.println("Please input valid email");
                email = scanner.nextLine();
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
}