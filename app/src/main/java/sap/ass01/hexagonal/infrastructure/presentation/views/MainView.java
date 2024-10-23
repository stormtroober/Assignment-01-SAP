package sap.ass01.hexagonal.infrastructure.presentation.views;

import sap.ass01.hexagonal.infrastructure.presentation.PresentationController;
import sap.ass01.hexagonal.infrastructure.presentation.dialogs.access.LoginDialog;
import sap.ass01.hexagonal.infrastructure.presentation.dialogs.access.RegisterDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame implements ActionListener {

    private JButton loginButton, registerButton;
    private final PresentationController presentationController;

    public MainView(PresentationController presentationController) {
        this.presentationController = presentationController;
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
                LoginDialog loginDialog = new LoginDialog(this, presentationController);
                loginDialog.setVisible(true);
            }).start();
        } else if (e.getSource() == registerButton) {
            new Thread(() -> {
                RegisterDialog registerDialog = new RegisterDialog(this, presentationController);
                registerDialog.setVisible(true);
            }).start();
        }
    }

    public static void main(String[] args) {
        PresentationController presentationController = new PresentationController(new ViewAdapter(new EBikeApplication()));
        MainView mainView = new MainView(presentationController);
        mainView.display();
    }
}