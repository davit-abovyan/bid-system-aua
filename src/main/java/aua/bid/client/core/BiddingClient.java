package aua.bid.client.core;


import aua.bid.server.core.RemoteController;
import aua.bid.Remote.Bid;

import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

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
        this.auctionNumber = new JTextArea(1, 3);
        this.email = new JTextArea(1, 20);
        this.price = new JTextArea(1, 8);
        this.initFramesAndActions();
        try {
            controller =
                    (RemoteController) Naming.lookup("rmi://127.0.0.1/Controller");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFramesAndActions() {
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton makeABid = new JButton("Make a bid");
        JButton makeABidByDefault = new JButton("Make a bid by default");
        JButton stopBidding = new JButton("Stop Bidding");


        makeABid.addActionListener(e -> makeABid());
        makeABidByDefault.addActionListener(e -> makeABidByDefault());
        stopBidding.addActionListener(e -> stopBidding());

        panel = new JPanel();
        panel.add(makeABid);
        panel.add(makeABidByDefault);
        panel.add(stopBidding);

        panel.add(new Label("Auction number"));
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
        try {
            controller.makeBid(auctionNumber.getText() +"##"+ email.getText()+"##"+price.getText());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void makeABidByDefault() {
        System.out.println("makeABidByDefault");
//        System.out.println(this.textArea.getText());

    }

    private void stopBidding() {
        System.out.println("stopBidding");
//        System.out.println(this.textArea.getText());
    }

    public void addToList(String line) {
        this.model.addElement(line);
    }
}
