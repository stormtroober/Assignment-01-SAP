package sap.ass01.layered.UI.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sap.ass01.layered.UI.Dialogs.LoginDialog;
import sap.ass01.layered.UI.Dialogs.RegisterDialog;

public class MainView extends JFrame implements ActionListener {

    private JButton loginButton, registerButton;

    public MainView() {
        setupView();
    }

    protected void setupView() {
        setTitle("Welcome");
        setSize(300, 100);
        setResizable(false);
        setLayout(new BorderLayout());

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void display() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            LoginDialog loginDialog = new LoginDialog(this);
            loginDialog.setVisible(true);
            loginDialog.getUserName().ifPresent(userName -> {
                if (userName.equals("admin")) {
                    new AdminView().display();
                } else if (userName.equals("user")) {
                    new UserView().display();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username");
                }
                this.dispose();
            });
        } else if (e.getSource() == registerButton) {
            // Handle sign up action
            JOptionPane.showMessageDialog(this, "Register button clicked");
        }
    }

    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.display();
    }
}