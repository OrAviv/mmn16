package q1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPanel extends JPanel
{
    private JList messageList;
    protected DefaultListModel messageListModel;
    private JScrollPane messageListScroller;

    private JList participantList;
    private DefaultListModel participantListModel;
    private JScrollPane participantListScroller;

    private JTextField textField;

    private JButton logout;
    public Client myClient;


    public ClientPanel(Client client)
    {
        super(new BorderLayout());

        this.myClient = client;
        messageListModel = new DefaultListModel();
        messageList = new JList(messageListModel);
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setVisibleRowCount(-1);
        messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageListScroller = new JScrollPane(messageList);
        messageListScroller.setPreferredSize(new Dimension(100, 200));

        participantListModel = new DefaultListModel();
        participantList = new JList(participantListModel);
        participantList.setLayoutOrientation(JList.VERTICAL);
        participantList.setVisibleRowCount(-1);
        participantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        participantListScroller = new JScrollPane(participantList);
        participantListScroller.setPreferredSize(new Dimension(100, 200));

        logout = new JButton("Logout");
        logout.setActionCommand("Logout");
        logout.addActionListener(new ClientPanel.logoutListener());

        //Create a panel that uses BoxLayout.
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JPanel textAreaPanel = new JPanel();
        textField = new JTextField("Start Writing...", 40);
        textField.addActionListener(new textFieldListener());
        textAreaPanel.add(textField);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(2));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(2));
        buttonPane.add(logout);
        buttonPane.add(Box.createHorizontalStrut(2));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(2));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bottomPanel.add(textAreaPanel);
        bottomPanel.add(buttonPane);

        add(messageListScroller, BorderLayout.CENTER);
        add(participantListScroller, BorderLayout.LINE_END);
        add(bottomPanel, BorderLayout.PAGE_END);

    }

    protected void addMessage(String message)
    {
        this.messageListModel.addElement(message);
    }

    protected void addParticipant(String participantName)
    {
        this.participantListModel.addElement(participantName);
    }

    protected void removeParticipant(String participantName)
    {
        this.participantListModel.removeElement(participantName);
    }

    public class textFieldListener implements ActionListener
    {
        String str;
        @Override
        public void actionPerformed(ActionEvent e)
        {
            str = textField.getText();
            myClient.sendMessage(str);
        }
    }


    public class logoutListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            myClient.startLogoutSequence();
        }
    }



}
