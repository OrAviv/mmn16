package q2;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

/**
 * @author Or Aviv
 */

// multicast Server class;
public class Server
{
    public static void main(String[] args)
    {
        String group = "230.0.0.1";
        String s;
        try
        {
            InetAddress inetAddress = InetAddress.getByName(group);
            DatagramSocket datagramSocket = new DatagramSocket();
            byte[] buffer = new byte[256];
            s = "connecting to server...";
            buffer = s.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 7777);
            datagramSocket.send(datagramPacket);
            while (true)
            {
                s = JOptionPane.showInputDialog(null, "Enter a string to send:");
                buffer = s.getBytes();
                datagramPacket.setData(buffer);
                datagramSocket.send(datagramPacket);
            }
        }
        catch (UnknownHostException e) {}
        catch (IOException e) {}

    }

}
