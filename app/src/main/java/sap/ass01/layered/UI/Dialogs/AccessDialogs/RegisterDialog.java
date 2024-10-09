package sap.ass01.layered.UI.Dialogs.AccessDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RegisterDialog extends AbstractDialog {

    private JTextField nameField;
    private JCheckBox adminCheckBox;

    public RegisterDialog(JFrame parent) {
        super(parent, "Register");
        setupDialog();
    }

    private void setupDialog() {
        nameField = new JTextField();
        adminCheckBox = new JCheckBox();
        addField("Name:", nameField);
        addField("Admin:", adminCheckBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == confirmButton) {
            String name = nameField.getText();
            boolean isAdmin = adminCheckBox.isSelected();
            if (!name.isEmpty()) {
                // Handle registration logic here
                JOptionPane.showMessageDialog(this, "Registration successful");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid name");
            }
        }
    }
}