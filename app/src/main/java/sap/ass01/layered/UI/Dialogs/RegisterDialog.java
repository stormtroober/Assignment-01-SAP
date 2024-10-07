package sap.ass01.layered.UI.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDialog extends JDialog implements ActionListener {

    private JTextField nameField;
    private JCheckBox adminCheckBox;
    private JButton registerButton, cancelButton;

    public RegisterDialog(JFrame parent) {
        super(parent, "Register", true);
        setupDialog();
    }

    private void setupDialog() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Admin:"));
        adminCheckBox = new JCheckBox();
        panel.add(adminCheckBox);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String name = nameField.getText();
            boolean isAdmin = adminCheckBox.isSelected();
            if (!name.isEmpty()) {
                // Handle registration logic here
                JOptionPane.showMessageDialog(this, "Registration successful");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid name");
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}