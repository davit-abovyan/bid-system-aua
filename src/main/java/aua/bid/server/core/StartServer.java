package aua.bid.server.core;

import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

import java.util.Timer;
import java.util.TimerTask;

public class StartServer extends JFrame {

    private static RemoteController controller;
    private boolean loggedIn = false;

    private final int countdownSeconds = 10;
    public final JTextArea admin;
    public final JTextArea auctionNumber;
    private JPanel panel;
    private DefaultListModel<String> userArea;
    private DefaultListModel<String> text;

    // IMPORTANT: before running server, start rmiregistry from ... src/main/java folder
    public static void main(String[] args) {
        new StartServer().setVisible(true);
    }


    private StartServer() {
        statCountDown((i) -> {
            System.out.println(i);
        });

        this.admin = new JTextArea(1, 20);
        this.auctionNumber = new JTextArea(1, 20);
        this.initFramesAndActions();
        editAdminText("Not logged in");

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
        JButton allBids = new JButton("All bids");


        login.addActionListener(e -> {
            login();
            admin.removeAll();
        });
        startAuction.addActionListener(e -> {
            startAuction();
            admin.removeAll();
        });
        finalizeResults.addActionListener(e -> {
            finalizeResults();
        });
        allBids.addActionListener(e -> {
            allBids();
        });

        panel = new JPanel();
        panel.add(login);
        panel.add(startAuction);
        panel.add(finalizeResults);
        panel.add(allBids);

        panel.add(new Label("Admin email goes here"));
        panel.add(admin);
        userArea = new DefaultListModel<>();
        panel.add(new JList<>(userArea));

        panel.add(new Label("Selected auction number"));
        panel.add(auctionNumber);

        text = new DefaultListModel<>();
        panel.add(new JList<>(text));


        this.getContentPane().add(panel);
    }

    public void addToList(String line) {
        this.text.addElement(line);
    }

    public void editAdminText(String line) {
        this.userArea.clear();
        this.userArea.addElement(line);
    }

    private void login() {
        try {
            loggedIn = controller.login(this);
            if (loggedIn) editAdminText(admin.getText());
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    private void startAuction() {
        if (loggedIn) {
            try {
                controller.startAuction(this);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        } else {
            addToList("You are not logged in, please login");
        }
    }

    private void finalizeResults() {
        if (loggedIn) {
            try {
                controller.finaliseResult(this);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        } else {
            addToList("You are not logged in, please login");
        }
    }

    private void statCountDown(EventEmitter eventListener) {
        final Timer timer = new Timer();
        int milisecondsInSeconds = 1000;
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = countdownSeconds;

            public void run() {
                i--;
                eventListener.emit(i);
                if (i < 0)
                    timer.cancel();

            }
        }, 0, 1 * milisecondsInSeconds);
    }

    private void allBids() {
        if (loggedIn) {
            try {
                controller.allBids(this);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        } else {
            addToList("You are not logged in, please login");
        }
    }
}
