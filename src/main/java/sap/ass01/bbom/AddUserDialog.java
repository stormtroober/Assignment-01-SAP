package sap.ass01.bbom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * Adapted from AddEBikeDialog
 * 
 */
public class AddUserDialog extends JDialog {

    private JTextField idField;
    private JButton okButton;
    private JButton cancelButton;
    private EBikeApp app;

    public AddUserDialog(EBikeApp owner) {
        super(owner, "Adding User", true);
        app = owner;
        initializeComponents();
        setupLayout();
        addEventHandlers();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initializeComponents() {
        idField = new JTextField(15);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
    }

    private void setupLayout() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("User ID:"));
        inputPanel.add(idField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addEventHandlers() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement OK button behavior here
                String id = idField.getText();
                app.addUser(id);                
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddUserDialog dialog = new AddUserDialog(null);
            dialog.setVisible(true);
        });
    }
}
