package q2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Or Aviv
 */

// this class is the client panel;
//   one scroll pane to accumulate the feed
//   one Boxlayout to accumulate the buttons.
public class ClientPanel extends JPanel
{
    private JList list;
    private DefaultListModel listModel;
    private JScrollPane listScroller;
    private JButton subscribeButton;
    private JButton unsubscribeButton;
    private JButton clearButton;
    public Client myClient;

    public ClientPanel()
    {
        super( new BorderLayout());

        listModel = new DefaultListModel();
        listModel.addElement("Dummy message.");
        list = new JList(listModel);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.myClient = new Client(listModel);

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
    }

    class SubscribeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            myClient = new Client(listModel);
            myClient.setSubscription(true);
            myClient.start();
        }
    }

    class UnsubscribeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            myClient.setSubscription(false);
            myClient.closeConnection();
        }
    }

    class ClearListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            listModel.clear();
        }
    }
}
