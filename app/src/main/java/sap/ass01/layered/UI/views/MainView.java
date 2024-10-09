package sap.ass01.layered.UI.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sap.ass01.layered.UI.Dialogs.AccessDialogs.LoginDialog;
import sap.ass01.layered.UI.Dialogs.AccessDialogs.RegisterDialog;
import sap.ass01.layered.services.Services.LoginService;
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
        LoginService loginService = businessImpl;
        if (e.getSource() == loginButton) {
            new Thread(() -> {
                LoginDialog loginDialog = new LoginDialog(this);
                loginDialog.setVisible(true);
                loginDialog.getUserName().ifPresent(userName -> {
                    loginService.logIn(userName).thenAccept(user -> {
                        if (user.admin()) {
                            JOptionPane.showMessageDialog(this, "Admin login successful");
                            new AdminView().display();
                        } else {
                            JOptionPane.showMessageDialog(this, "User login successful");
                            new UserView().display();
                        }
                    });
                    JOptionPane.showMessageDialog(this, "Login successful");
                });
            }).start();
        } else if (e.getSource() == registerButton) {
            new Thread(() -> {
                RegisterDialog registerDialog = new RegisterDialog(this);
                registerDialog.setVisible(true);
                registerDialog.getUserName().ifPresent(userName -> {
                    boolean isAdmin = registerDialog.isAdmin();
                    loginService.signIn(userName, isAdmin);
                    JOptionPane.showMessageDialog(this, "Registration successful");
                });
            }).start();
        }
    }



    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.display();
    }
}