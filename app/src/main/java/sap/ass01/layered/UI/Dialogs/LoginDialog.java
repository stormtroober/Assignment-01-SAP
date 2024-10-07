package sap.ass01.layered.UI.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class LoginDialog extends JDialog implements ActionListener {

    private JTextField userNameField;
    private JButton loginButton, cancelButton;
    private Optional<String> userName = Optional.empty();

    public LoginDialog(JFrame parent) {
        super(parent, "Login", true);
        setupDialog();
    }

    private void setupDialog() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Username:"));
        userNameField = new JTextField();
        panel.add(userNameField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            userName = Optional.ofNullable(userNameField.getText());
            if (userName.isPresent() && !userName.get().isEmpty()) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid username");
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public Optional<String> getUserName() {
        return userName;
    }
}