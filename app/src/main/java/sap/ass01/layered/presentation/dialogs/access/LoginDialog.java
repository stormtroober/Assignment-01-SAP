package sap.ass01.layered.presentation.dialogs.access;

import sap.ass01.frontend.models.UserViewModel;
import sap.ass01.frontend.views.AdminView;
import sap.ass01.frontend.views.UserView;
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
//                loginService.logIn(userName.get()).thenAccept(user -> {
//                    if (user.admin()) {
//                        JOptionPane.showMessageDialog(this, "Admin login successful");
//                        new AdminView().display();
//                    } else {
//                        JOptionPane.showMessageDialog(this, "User login successful");
//                        new UserView().display();
//                    }
//                    dispose();
//                });
                /*loginService.logIn(userName.get()).subscribe(user -> {
                    if (user.admin()) {
                        JOptionPane.showMessageDialog(this, "Admin login successful");
                        new AdminView(Mapper.toDomain(user)).display();
                    } else {
                        JOptionPane.showMessageDialog(this, "User login successful");
                        new UserView(Mapper.toDomain(user)).display();
                    }
                    dispose();
                }, error -> {
                    JOptionPane.showMessageDialog(this, "Login failed: " + error.getMessage());
                });*/


            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid username");
            }
        }
    }

    private void handleLoginSuccess(UserViewModel user) {
        if (user.admin()) {
            JOptionPane.showMessageDialog(this, "Admin login successful");
            new AdminView(user).display(); // Show Admin View
        } else {
            JOptionPane.showMessageDialog(this, "User login successful");
            new UserView(user).display(); // Show User View
        }
        dispose(); // Close the dialog after successful login
    }

    private void handleLoginFailure(Throwable error) {
        JOptionPane.showMessageDialog(this, "Login failed: " + error.getMessage());
    }

    public Optional<String> getUserName() {
        return userName;
    }
}