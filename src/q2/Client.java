package q2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

/**
 * @author Or Aviv
 */
public class Client extends JPanel {
    String group = "230.0.0.1";
    InetAddress inetAddress;
    MulticastSocket multicastSocket;
    DatagramPacket datagramPacket;
    byte[] buffer;
    String received;

    boolean isSubscribe;

    JList list;
    DefaultListModel listModel;
    JScrollPane listScroller;
    JButton subscribeButton;
    JButton unsubscribeButton;
    JButton clearButton;

    public Client() {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        listModel.addElement("Dummy message.");
        list = new JList(listModel);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(100, 200));

        subscribeButton = new JButton("Subscribe");
        subscribeButton.setActionCommand("Subscribe");
        subscribeButton.addActionListener(new SubscribeListener());

        unsubscribeButton = new JButton("Un-subscribe");
        unsubscribeButton.setActionCommand("Un-subscribe");
        unsubscribeButton.addActionListener(new UnsubscribeListener());

        clearButton = new JButton("Clear feed");
        clearButton.setActionCommand("Clear");
        clearButton.addActionListener(new ClearListener());

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(subscribeButton);
        buttonPane.add(Box.createHorizontalStrut(2));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(2));
        buttonPane.add(clearButton);
        buttonPane.add(Box.createHorizontalStrut(2));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(2));
        buttonPane.add(unsubscribeButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScroller, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);
        createConnection();
    }

    private void createConnection()
    {
        try
        {
            inetAddress = InetAddress.getByName(group);
            multicastSocket = new MulticastSocket(7777);
            buffer = new byte[256];

        }
        catch (UnknownHostException e) {}
        catch (IOException e) {}
    }

    public void subscribe()
    {
        try
        {
            multicastSocket.joinGroup(inetAddress);
            buffer = "Dummy".getBytes();
            datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 7777);
            // dummy packet for establishing connection.
            multicastSocket.send(datagramPacket);
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            /**subscribing*/
            while (isSubscribe)
            {
                multicastSocket.receive(datagramPacket);
                received = new String(datagramPacket.getData(),datagramPacket.getOffset(),datagramPacket.getLength());
                listModel.addElement(received);
                System.err.println("Received " + datagramPacket.getLength() +
                        " bytes from " + datagramPacket.getAddress()+" DATA:"+ received);
            }
        }

        catch (UnknownHostException e) {}
        catch (IOException e) {}
    }

    class SubscribeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            isSubscribe = true;
            subscribe();
        }
    }

    class UnsubscribeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                /**un-subscribe*/
                multicastSocket.leaveGroup(inetAddress);
    //            multicastSocket.close();
                isSubscribe = false;
                listModel.clear();

            }
            catch (IOException ex){}
        }
    }

    class ClearListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            listModel.clear();
        }
    }

    private static void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame frame = new JFrame("My Client Feed");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 600));
        Client clientPanel = new Client();
        frame.add(clientPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        createAndShowGUI();

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable()
//        {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
    }

}


