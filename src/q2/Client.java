package q2;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

/**
 * @author Or Aviv
 */
public class Client extends Thread
{
    private final String group = "230.0.0.1";
    private InetAddress inetAddress;
    private MulticastSocket multicastSocket;
    private DatagramPacket packet;
    private byte[] buffer;
    private boolean isSubscribe;
    private DefaultListModel listModel;

    // Constructor.
    public Client(DefaultListModel listModel)
    {
        this.setSubscription(false);
        this.setListModel(listModel);
        this.buffer = new byte[256];
        this.packet = new DatagramPacket(buffer, buffer.length);

    }

    // new connection is made for every subscription.
    public void run()
    {
        createConnection();
        if (isSubscribe)
            subscribe();
    }

    // only creates connection.
    protected void createConnection()
    {
        try
        {
            this.inetAddress = InetAddress.getByName(group);
            this.multicastSocket = new MulticastSocket(7777);
        }
        catch (UnknownHostException e) {}
        catch (IOException e) {}
    }


    // joins the multicast and fills the feed with new inputs.
    protected void subscribe()
    {
        try
        {
            this.multicastSocket.joinGroup(inetAddress);
            while (isSubscribe)
            {
                multicastSocket.receive(packet);
                this.listModel.addElement(new String(packet.getData(), packet.getOffset(), packet.getLength()));
            }
        }
        catch (IOException e) {}
    }

    // closes connection by leaving the multicast group.
    protected void closeConnection()
    {
        try
        {
            multicastSocket.leaveGroup(inetAddress);
            this.listModel.clear();
        }
        catch (IOException e) {}
    }

    // setter.
    protected void setSubscription(boolean isSubscribe)
    {
        this.isSubscribe = isSubscribe;
    }

    // setter
    protected void setListModel(DefaultListModel listModel)
    {
        this.listModel = listModel;
    }

}