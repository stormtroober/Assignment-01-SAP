/*package sap.ass01.layered.ui.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sap.ass01.layered.ui.dialogs.access.LoginDialog;
import sap.ass01.layered.ui.dialogs.access.RegisterDialog;

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
            new Thread(() -> {
                LoginDialog loginDialog = new LoginDialog(this);
                loginDialog.setVisible(true);
            }).start();
        } else if (e.getSource() == registerButton) {
            new Thread(() -> {
                RegisterDialog registerDialog = new RegisterDialog(this);
                registerDialog.setVisible(true);
            }).start();
        }
    }

    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.display();
    }
}*/