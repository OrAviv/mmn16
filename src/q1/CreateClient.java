package q1;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CreateClient
{
    public static void main(String[] args)
    {
        Socket socket;
        try
        {
            String server = JOptionPane.showInputDialog(null,
                    "Enter your Server name: \n Default is 'localhost'");
            if (server.isBlank())
                socket = new Socket("localhost", 7777);
            else
                socket = new Socket(server, 7777);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {}


    }
}
