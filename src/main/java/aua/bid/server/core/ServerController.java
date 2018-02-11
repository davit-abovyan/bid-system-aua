package aua.bid.server.core;

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ServerController extends UnicastRemoteObject implements RemoteController {

    private static int lastAuctionNumber = 0;

    protected ServerController() throws RemoteException {
        super();
    }

    public void makeBid(int bidderNumber)  throws RemoteException {
        System.out.println("Bidder N" + bidderNumber + ": please provide your email and next your price:");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.next();
        int price = scanner.nextInt();
        try(FileWriter fw = new FileWriter(lastAuctionNumber+".txt", true);
            BufferedWriter bw = new BufferedWriter(fw) ) {
            bw.write(email+"##"+price+"\n");
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (Exception e){
            System.out.println("Something went wrong");
        }
    }

    public boolean login() throws RemoteException{
        final String adminFileName = "admin.txt";
        try (FileReader fr = new FileReader(adminFileName);
             BufferedReader br = new BufferedReader(fr)) {
            String email;
            if ((email = br.readLine()) != null) {
                while (true) {
                    System.out.println("Please input admin email: ");
                    Scanner scanner = new Scanner(System.in);
                    if (email.equals(scanner.nextLine()))
                        return true;
                    else
                        System.out.println("Wrong email.");
                }
            } else {
                System.out.println("Something went wrong, please restart application.");
            }
        } catch (FileNotFoundException e) {
            createAdminRecord(adminFileName);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void finaliseResult(int auction) throws RemoteException{

        try (FileReader fr = new FileReader(auction+".txt");
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
            System.out.println("If you want another auction Y/N?");
            Scanner scanner = new Scanner(System.in);
            if ("Y".equals(scanner.nextLine()))
                startAuction();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void startAuction() throws RemoteException{
        ++lastAuctionNumber;
        System.out.println("Starting auction N"+lastAuctionNumber);
        boolean isLogined = login();
        if(isLogined){
            int bidNumber = 3;
            for(int i = 0; i<bidNumber;++i) {
                makeBid(i+1);
            }
            finaliseResult(lastAuctionNumber);
        } else {
            System.out.println("Something went wrong, please restart application.");
        }
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