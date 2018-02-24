package aua.bid.client.core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI extends JFrame {
    private final JTextArea textArea;
    private JPanel panel;
    private DefaultListModel<String> model;

    public static void main(String[] args) {
        new ClientUI().setVisible(true);
    }

    private ClientUI() {
        this.textArea = new JTextArea(20, 60);
        this.initFramesAndActions();
        addToList("one");
        addToList("one");
        addToList("one");
        addToList("one");
    }

    private void initFramesAndActions() {
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton makeABid = new JButton("Make a bid");
        JButton makeABidByDefault = new JButton("Make a bid by default");
        JButton stopBidding = new JButton("Stop Bidding");


        makeABid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeABid();
            }
        });
        makeABidByDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeABidByDefault();
            }
        });
        stopBidding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopBidding();
            }
        });

        panel = new JPanel();
        panel.add(makeABid);
        panel.add(makeABidByDefault);
        panel.add(stopBidding);

        panel.add(textArea);

        model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        panel.add(list);

        this.getContentPane().add(panel);
    }

    private void makeABid() {
        System.out.println("makeABid");
        System.out.println(this.textArea.getText());
    }

    private void makeABidByDefault() {
        System.out.println("makeABidByDefault");
        System.out.println(this.textArea.getText());

    }

    private void stopBidding() {
        System.out.println("stopBidding");
        System.out.println(this.textArea.getText());
    }

    public void addToList(String line) {
        this.model.addElement(line);
    }
}
