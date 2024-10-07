package sap.ass01.layered.UI.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class BaseView extends JFrame {

    protected JPanel topPanel;
    protected JPanel centralPanel;

    public BaseView(String title) {
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        centralPanel = new JPanel();
        add(centralPanel, BorderLayout.CENTER);
    }

    protected void addTopPanelButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        topPanel.add(button);
    }

    public void display() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }
}