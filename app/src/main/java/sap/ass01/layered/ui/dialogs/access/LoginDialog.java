package sap.ass01.layered.ui.dialogs.access;

import sap.ass01.layered.ui.dialogs.AbstractDialog;
import sap.ass01.layered.ui.mapper.Mapper;
import sap.ass01.layered.ui.views.AdminView;
import sap.ass01.layered.ui.views.UserView;
import sap.ass01.layered.services.Services.LoginService;
import sap.ass01.layered.services.impl.ServiceFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class LoginDialog extends AbstractDialog {

    private JTextField userNameField;
    private Optional<String> userName = Optional.empty();
    private final LoginService loginService = ServiceFactory.getLoginService();

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
                loginService.logIn(userName.get()).subscribe(user -> {
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