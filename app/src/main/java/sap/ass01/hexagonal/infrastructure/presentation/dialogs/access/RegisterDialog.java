package sap.ass01.hexagonal.infrastructure.presentation.dialogs.access;

import sap.ass01.hexagonal.infrastructure.presentation.PresentationController;
import sap.ass01.hexagonal.infrastructure.presentation.dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class RegisterDialog extends AbstractDialog {

    private JTextField nameField;
    private JCheckBox adminCheckBox;
    private final PresentationController presentationController;

    public RegisterDialog(JFrame parent, PresentationController presentationController) {
        super(parent, "Register");
        this.presentationController = presentationController;
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
                presentationController.signUp(
                        name,
                        isAdmin,
                        () -> { // On success callback
                            JOptionPane.showMessageDialog(this, "Registration successful");
                            dispose();
                        },
                        error -> { // On error callback
                            JOptionPane.showMessageDialog(this, "Registration failed: " + error.getMessage());
                        }
                );
//                loginService.signIn(name, isAdmin).thenRun(() -> {
//                    JOptionPane.showMessageDialog(this, "Registration successful");
//                    dispose();
//                });
                /*loginService.signUp(name, isAdmin).subscribe(() -> {
                    JOptionPane.showMessageDialog(this, "Registration successful");
                    dispose();
                }, error -> {
                    JOptionPane.showMessageDialog(this, "Registration failed: " + error.getMessage());
                });*/
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