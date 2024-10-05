package sap.ass01.bbom;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEBikeDialog extends JDialog {

    private JTextField idField;
    private JTextField xCoordField;
    private JTextField yCoordField;
    private JButton okButton, cancelButton;
    private EBikeService ebikeService;

    public AddEBikeDialog(JFrame owner, EBikeService service) {
        super(owner, "Adding E-Bike", true);
        this.ebikeService = service;
        initializeComponents();
        setupLayout();
        addEventHandlers();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initializeComponents() {
        idField = new JTextField(15);
        xCoordField = new JTextField(15);
        yCoordField = new JTextField(15);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
    }

    private void setupLayout() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("E-Bike ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("E-Bike location - X coord:"));
        inputPanel.add(xCoordField);
        inputPanel.add(new JLabel("E-Bike location - Y coord:"));
        inputPanel.add(yCoordField);

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
                String id = idField.getText();
                double xCoord = Double.parseDouble(xCoordField.getText());
                double yCoord = Double.parseDouble(yCoordField.getText());
                ebikeService.addEBike(id, new P2d(xCoord, yCoord));  // Use the service layer to add a bike
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
}
