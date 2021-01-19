package q1;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CreateClient
{
    public CreateClient()
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
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            try
            {
                Object input = in.readObject();
                while (true)
                {
                    if (input instanceof Client)
                        break;
                    input = in.readObject();
                }
                Client c = (Client) input;
                c.start();
            }
            catch (IOException | ClassNotFoundException e) {}
        }

        catch (IOException e) {}
    }

    public static void main(String[] args)
    {
        CreateClient c = new CreateClient();
    }
}
