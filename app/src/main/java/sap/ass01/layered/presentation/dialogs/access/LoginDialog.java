package sap.ass01.layered.presentation.dialogs.access;

import sap.ass01.layered.presentation.models.UserViewModel;
import sap.ass01.layered.presentation.views.AdminView;
import sap.ass01.layered.presentation.views.UserView;
import sap.ass01.layered.presentation.PresentationController;
import sap.ass01.layered.presentation.dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class LoginDialog extends AbstractDialog {

    private JTextField userNameField;
    private Optional<String> userName = Optional.empty();
    private final PresentationController presentationController = new PresentationController();

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
                presentationController.logIn(userName.get(),
                        this::handleLoginSuccess,
                        this::handleLoginFailure);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid username");
            }
        }
    }

    private void handleLoginSuccess(UserViewModel user) {
        if (user.admin()) {
            JOptionPane.showMessageDialog(this, "Admin login successful");
            new AdminView(user).display();
        } else {
            JOptionPane.showMessageDialog(this, "User login successful");
            new UserView(user).display();
        }
        dispose();
    }

    private void handleLoginFailure(Throwable error) {
        JOptionPane.showMessageDialog(this, "Login failed: " + error.getMessage());
    }

    public Optional<String> getUserName() {
        return userName;
    }
}