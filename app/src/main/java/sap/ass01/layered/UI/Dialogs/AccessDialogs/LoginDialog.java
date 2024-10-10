package sap.ass01.layered.UI.Dialogs.AccessDialogs;

import sap.ass01.layered.UI.Dialogs.AbstractDialog;
import sap.ass01.layered.UI.views.AdminView;
import sap.ass01.layered.UI.views.UserView;
import sap.ass01.layered.services.Services.LoginService;
import sap.ass01.layered.services.impl.BusinessImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class LoginDialog extends AbstractDialog {

    private JTextField userNameField;
    private Optional<String> userName = Optional.empty();
    private final LoginService loginService;

    public LoginDialog(JFrame parent, LoginService loginService) {
        super(parent, "Login");
        this.loginService = loginService;
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
                loginService.logIn(userName.get()).thenAccept(user -> {
                     if (user.admin()) {
                         JOptionPane.showMessageDialog(this, "Admin login successful");
                         new AdminView().display();
                     } else {
                         JOptionPane.showMessageDialog(this, "User login successful");
                         new UserView().display();
                     }
                     dispose();
                });

            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid username");
            }
        }
    }

    public Optional<String> getUserName() {
        return userName;
    }
}