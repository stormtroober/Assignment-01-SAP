package sap.ass01.layered.UI.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class LoginDialog extends JDialog implements ActionListener {

    private JTextField userCodeField;
    private JButton loginButton, cancelButton;
    private Optional<String> userCode = Optional.empty();

    public LoginDialog(JFrame parent) {
        super(parent, "Login", true);
        setupDialog();
    }

    private void setupDialog() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("User Code:"));
        userCodeField = new JTextField();
        panel.add(userCodeField);

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
            userCode = Optional.ofNullable(userCodeField.getText());
            if (userCode.isPresent() && !userCode.get().isEmpty()) {
                // Handle login logic here
                JOptionPane.showMessageDialog(this, "Login successful");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid user code");
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public Optional<String> getUserCode() {
        return userCode;
    }
}
