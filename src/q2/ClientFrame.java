package q2;

import javax.swing.*;
import java.awt.*;
/**
 * @author Or Aviv
 */
public class ClientFrame extends JFrame
{
    private JFrame frame;

    public ClientFrame()
    {
        frame = new JFrame("My Client Feed");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 600));
        ClientPanel clientPanel = new ClientPanel();
        frame.add(clientPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main (String[] args)
    {
        ClientFrame myClientFrame = new ClientFrame();
    }
}
