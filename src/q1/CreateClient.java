package q1;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CreateClient
{
    private ObjectInputStream input;
    private Socket socket;

    public CreateClient()
    {
        try
        {
            String server = JOptionPane.showInputDialog(null,
                    "Enter your Server name: \n Default is 'localhost'");
            if (server.isBlank())
                socket = new Socket("localhost", 7777);
            else
                socket = new Socket(server, 7777);
            this.input = new ObjectInputStream(socket.getInputStream());

            Object object = input.readObject();
            while (true)
            {
                if (object instanceof Client)
                    break;
                object = input.readObject();
            }
            Client c = (Client) object;
            c.start();
        }
        catch (IOException | ClassNotFoundException e) {}

    }

    public static void main(String[] args)
    {
        CreateClient c = new CreateClient();
    }
}
