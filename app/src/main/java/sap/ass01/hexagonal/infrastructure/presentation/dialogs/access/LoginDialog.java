package sap.ass01.hexagonal.infrastructure.presentation.dialogs.access;

import sap.ass01.hexagonal.infrastructure.presentation.PresentationController;
import sap.ass01.hexagonal.infrastructure.presentation.dialogs.AbstractDialog;
import sap.ass01.hexagonal.infrastructure.presentation.models.UserViewModel;
import sap.ass01.hexagonal.infrastructure.presentation.views.AdminView;
import sap.ass01.hexagonal.infrastructure.presentation.views.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class LoginDialog extends AbstractDialog {

    private JTextField userNameField;
    private Optional<String> userName = Optional.empty();
    private final PresentationController presentationController;

    public LoginDialog(JFrame parent, PresentationController presentationController) {
        super(parent, "Login");
        this.presentationController = presentationController;
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
            new AdminView(user, presentationController).display(); // Show Admin View
        } else {
            JOptionPane.showMessageDialog(this, "User login successful");
            new UserView(user, presentationController).display(); // Show User View
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