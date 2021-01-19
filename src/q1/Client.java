package q1;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
    public static final int port = 7777;
    private boolean isConnected = false;
    private Socket socket;
    private ObjectInputStream inputStream;
    private String host = "localhost";
    private String computerName;
    private ObjectOutputStream outputStream;
    private Server myServer;
    private String message;
    private ClientFrame clientGUI;


    public Client(Socket socket, Server server) throws IOException {
        this.computerName = JOptionPane.showInputDialog(null, "Enter your name:");
        this.myServer = server;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.socket = new Socket(this.host, this.port);
        System.out.println("After connection");
        this.isConnected = true;
        this.clientGUI = new ClientFrame(this);
        this.sendMessage(this.computerName + " joins the chat.");
        this.myServer.addNewParticipant(this.getComputerName());
        this.getParticipantList();
    }

    @Override
    public void run()
    {
        try {
            while (isConnected)
            {
                this.message = (String) this.inputStream.readObject();
                this.clientGUI.clientPanel.addMessage(this.message);
            }
        } catch (IOException | ClassNotFoundException e) {
        } finally
        {
            this.closeConnections();
        }
    }


    public void sendMessage(String message) {
        this.myServer.sendMessageToAllClients(message);
    }

    public void closeConnections() {
        try {
            this.outputStream.close();
            this.inputStream.close();
            this.socket.close();
            this.myServer.logoutClient(this);
        } catch (IOException e) {
        }
    }

    public String getComputerName()
    {
        return this.computerName;
    }

    private void getParticipantList()
    {
        for (Client client : this.myServer.getParticipantList())
        {
            this.clientGUI.clientPanel.addParticipant(client.getComputerName());
        }
    }

    public void removeLogoutClient(String name)
    {
        this.clientGUI.clientPanel.removeParticipant(name);
    }

    public void addNewParticipant(String name)
    {
        this.clientGUI.clientPanel.addParticipant(name);
    }

    protected void startLogoutSequence ()
    {
        this.isConnected = false;
    }

}

