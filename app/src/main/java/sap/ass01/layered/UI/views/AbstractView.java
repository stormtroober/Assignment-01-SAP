package sap.ass01.layered.UI.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractView extends JFrame {

    protected JPanel topPanel;
    protected JPanel centralPanel;
    protected JButton logoutButton;
    protected JPanel buttonPanel;
    private boolean isAdmin;
    private long dx;
    private long dy;

    public AbstractView(String title) {
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        centralPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintCentralPanel(g);
            }
        };
        add(centralPanel, BorderLayout.CENTER);

        dx = 800 / 2 - 20;
        dy = 500 / 2 - 20;

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        topPanel.add(logoutButton, BorderLayout.EAST);
    }

    protected void addTopPanelButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        topPanel.add(button);
    }

    public void updateVisualizerPanel(boolean isAdmin) {
        this.isAdmin = isAdmin;
        centralPanel.repaint();
    }

    protected void paintCentralPanel(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());

        if (isAdmin) {
            // Placeholder for e-bikes
            g2.drawOval((int) dx, (int) dy, 20, 20);
            g2.drawString("E-Bike ID - battery", (int) dx, (int) dy + 35);
            g2.drawString("(x, y)", (int) dx, (int) dy + 50);
            g2.drawString("STATUS", (int) dx, (int) dy + 65);

            // Placeholder for users
            int y = 20;
            g2.drawRect(10, y, 20, 20);
            g2.drawString("User ID - credit: N/A", 35, y + 15);
        } else {
            // Placeholder for e-bikes
            g2.drawOval((int) dx, (int) dy, 20, 20);
            g2.drawString("E-Bike ID - battery", (int) dx, (int) dy + 35);
            g2.drawString("(x, y)", (int) dx, (int) dy + 50);

            int y = 20;
            g2.drawRect(10, y, 20, 20);
            g2.drawString("EBike ID - battery: N/A", 35, y + 15);
        }
    }

    public void display() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }
}