package aua.bid.server.core;

import javax.swing.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class StartServer extends JFrame  {

    private static RemoteController controller;
    private boolean loggedIn = false;

    public final JTextArea textArea;
    private JPanel panel;
    private DefaultListModel<String> model;

    // IMPORTANT: before running server, start rmiregistry from ... src/main/java folder
    public static void main(String[] args){
        new StartServer().setVisible(true);
    }


    private StartServer() {
        this.textArea = new JTextArea(3, 40);
        this.initFramesAndActions();
        addToList("You are not logged in, please login");

        try {
            controller = new ServerController();
            Naming.rebind("Controller", controller);

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private void initFramesAndActions() {

        setSize(500, 250);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton login = new JButton("Login");
        JButton startAuction = new JButton("Start auction");
        JButton finalizeResults = new JButton("Finalize results");


        login.addActionListener(e -> {
            login();
            textArea.removeAll();
        });
        startAuction.addActionListener(e -> {
            startAuction();
            textArea.removeAll();
        });
        finalizeResults.addActionListener(e -> {
            finalizeResults();
        });

        panel = new JPanel();
        panel.add(login);
        panel.add(startAuction);
        panel.add(finalizeResults);

        panel.add(textArea);

        model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        panel.add(list);

        this.getContentPane().add(panel);
    }

    public void addToList(String line) {
        this.model.clear();
        this.model.addElement(line);
    }

    private void login(){
        try {
            loggedIn = controller.login(this);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    private void startAuction(){
        if(loggedIn){
            try {
                controller.startAuction(this);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        } else {
            addToList("You are not logged in, please login");
        }
    }

    private void finalizeResults(){
        if(loggedIn){
            try {
                controller.finaliseResult(this);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        } else {
            addToList("You are not logged in, please login");
        }
    }
}
