package q1;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame
{
    private JFrame frame;
    protected ClientPanel clientPanel;

    public ClientFrame(Client client)
    {
        frame = new JFrame("My Client Feed");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 600));
        this.clientPanel = new ClientPanel(client);
        frame.add(this.clientPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
