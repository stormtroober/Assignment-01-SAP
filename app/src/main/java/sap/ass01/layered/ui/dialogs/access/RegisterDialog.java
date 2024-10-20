package sap.ass01.layered.ui.dialogs.access;

import sap.ass01.layered.ui.dialogs.AbstractDialog;
import sap.ass01.layered.services.Services.LoginService;
import sap.ass01.layered.services.impl.ServiceFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class RegisterDialog extends AbstractDialog {

    private JTextField nameField;
    private JCheckBox adminCheckBox;
    private final LoginService loginService = ServiceFactory.getLoginService();

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
//                loginService.signIn(name, isAdmin).thenRun(() -> {
//                    JOptionPane.showMessageDialog(this, "Registration successful");
//                    dispose();
//                });
                loginService.signUp(name, isAdmin).subscribe(() -> {
                    JOptionPane.showMessageDialog(this, "Registration successful");
                    dispose();
                }, error -> {
                    JOptionPane.showMessageDialog(this, "Registration failed: " + error.getMessage());
                });
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid name");
            }
        }
    }

    public Optional<String> getUserName() {
        return Optional.ofNullable(nameField.getText());
    }

    public boolean isAdmin() {
        return adminCheckBox.isSelected();
    }
}