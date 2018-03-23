package aua.bid.client.core;


import aua.bid.server.core.RemoteController;
import aua.bid.Remote.Bid;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BiddingClient extends JFrame {

    private static RemoteController controller;

    public final JTextArea auctionNumber;
    public final JTextArea email;
    public final JTextArea price;
    private JPanel panel;
    private DefaultListModel<String> model;

    public static void main(String[] args) {
        new BiddingClient().setVisible(true);
    }

    private BiddingClient() {
        this.auctionNumber = new JTextArea(1, 40);
        this.email = new JTextArea(1, 40);
        this.price = new JTextArea(1, 40);
        this.initFramesAndActions();
        try {
            controller =
                    (RemoteController) Naming.lookup("rmi://127.0.0.1/Controller");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFramesAndActions() {
        setSize(500, 400);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton makeABid = new JButton("Make a nid");
        JButton requestBidStatus = new JButton("Request bid status");


        makeABid.addActionListener(e -> makeABid());
        requestBidStatus.addActionListener(e -> requestBidStatus());

        panel = new JPanel();
        panel.add(makeABid);
        panel.add(requestBidStatus);

        panel.add(new Label("-              Auction number"));
        panel.add(auctionNumber);
        panel.add(new Label("Your email"));
        panel.add(email);
        panel.add(new Label("Your bid in AMD"));
        panel.add(price);

        model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        panel.add(list);

        this.getContentPane().add(panel);
    }

    private void makeABid() {
        Set<String> emails = new HashSet<>();
        try {
            try (FileReader fr = new FileReader(auctionNumber.getText()+".txt");
                 BufferedReader br = new BufferedReader(fr)) {
                String row;
                while ((row = br.readLine()) != null) {
                    String[] bid = row.split("##");
                    emails.add(bid[0]);
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }

            if(emails.contains(email.getText())){
                addToList("The user "+ email.getText() + " has already participated to auction N"+auctionNumber.getText());
                return;
            }

            boolean result = controller.makeBid(auctionNumber.getText() +"##"+ email.getText()+"##"+price.getText());
            if(result)
                addToList("Bid on Auction N"+auctionNumber.getText()+" for user "+ email.getText() + " is recorded");
            else
                addToList("No new bid allowed for Auction N"+auctionNumber.getText());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void requestBidStatus() {
        try (FileReader fr = new FileReader(auctionNumber.getText()+".txt");
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
        }catch (IOException ex){
            ex.printStackTrace();
        }
            addToList(email+" won with price "+price);
    }

    public void addToList(String line) {
        this.model.clear();
        this.model.addElement(line);
    }
}
