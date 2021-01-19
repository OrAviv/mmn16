package q1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**@author Or Aviv*/
public class Server
{
    public static final int port = 7777;

    private ServerSocket serverSocket;
    private ObjectOutputStream outputStream;
    public ArrayList<Client> activeClients;

    public Server()
    {
        this.activeClients = new ArrayList<>();
        try
        {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server's ready");
            while (true)
            {
                Socket socket = this.serverSocket.accept();
                this.outputStream = new ObjectOutputStream(socket.getOutputStream());
                Client client = new Client(socket, this);
                this.outputStream.writeObject(client);
                this.activeClients.add(client);
                client.start();
            }

        }
        catch(IOException e) {e.printStackTrace();}
        finally
        {
            try {serverSocket.close();}
            catch (IOException e) {}
        }
    }

    public void sendMessageToAllClients(String message)
    {
        for (Client client : activeClients)
            client.sendMessage(message);
    }

    public ArrayList<Client> getParticipantList()
    {
        return this.activeClients;
    }

    public void addNewParticipant(String name)
    {
        for (Client client : this.activeClients)
        {
            client.addNewParticipant(name);
        }
    }

    public void logoutClient(Client client)
    {
        this.sendMessageToAllClients(client.getComputerName()+" logged out.");
        this.activeClients.remove(client);
        for (Client otherClient : this.activeClients)
        {
            otherClient.removeLogoutClient(client.getComputerName());
        }
    }

    public static void main (String[] args)
    {
        Server server = new Server();
    }



}
