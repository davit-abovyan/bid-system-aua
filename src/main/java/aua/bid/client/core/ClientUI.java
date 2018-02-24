package aua.bid.client.core;

import javax.swing.*;

public class ClientUI extends JFrame {
    public static void main(String[] args) {
        new ClientUI().setVisible(true);
    }

    private ClientUI() {
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
                makeABid();
            }
        });
        stopBidding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeABid();
            }
        });

        JPanel panel = new JPanel();
        panel.add(makeABid);
        panel.add(makeABidByDefault);
        panel.add(stopBidding);

        JTextArea textArea = new JTextArea(20, 30);
        panel.add(textArea);

        this.getContentPane().add(panel);
    }

    public void makeABid() {
        System.out.ptintnl('makeABid');
    }

    public void makeABidByDefault() {
        System.out.ptintnl('makeABidByDefault');

    }

    public void stopBidding() {
        System.out.ptintnl('stopBidding');

    }
}
