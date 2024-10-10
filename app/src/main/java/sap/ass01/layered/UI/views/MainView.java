package sap.ass01.layered.UI.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sap.ass01.layered.UI.Dialogs.AccessDialogs.LoginDialog;
import sap.ass01.layered.UI.Dialogs.AccessDialogs.RegisterDialog;
import sap.ass01.layered.services.impl.BusinessImpl;

public class MainView extends JFrame implements ActionListener {

    private JButton loginButton, registerButton;
    private final BusinessImpl businessImpl;

    public MainView() {
        setupView();
        businessImpl = new BusinessImpl();
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
                LoginDialog loginDialog = new LoginDialog(this, businessImpl);
                loginDialog.setVisible(true);
            }).start();
        } else if (e.getSource() == registerButton) {
            new Thread(() -> {
                RegisterDialog registerDialog = new RegisterDialog(this, businessImpl);
                registerDialog.setVisible(true);
            }).start();
        }
    }

    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.display();
    }
}