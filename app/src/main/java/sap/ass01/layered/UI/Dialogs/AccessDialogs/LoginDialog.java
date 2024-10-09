package sap.ass01.layered.UI.Dialogs.AccessDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class LoginDialog extends AbstractDialog {

    private JTextField userNameField;
    private Optional<String> userName = Optional.empty();

    public LoginDialog(JFrame parent) {
        super(parent, "Login");
        setupDialog();
    }

    private void setupDialog() {
        userNameField = new JTextField();
        addField("Username:", userNameField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == confirmButton) {
            userName = Optional.ofNullable(userNameField.getText());
            if (userName.isPresent() && !userName.get().isEmpty()) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid username");
            }
        }
    }

    public Optional<String> getUserName() {
        return userName;
    }
}