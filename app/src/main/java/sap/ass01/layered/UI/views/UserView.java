package sap.ass01.layered.UI.views;

import sap.ass01.layered.UI.Dialogs.UserDialogs.RechargeCreditDialog;
import sap.ass01.layered.UI.Dialogs.UserDialogs.StartRideDialog;
import sap.ass01.layered.services.Services.UserService;
import sap.ass01.layered.services.impl.ServiceFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends AbstractView {

    private final UserService userService = ServiceFactory.getUserService();

    public UserView() {
        super("User View");
        setupView();
        refreshView();
    }

    private void setupView() {
        addTopPanelButton("Start Ride", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartRideDialog startRideDialog = new StartRideDialog(UserView.this);
                startRideDialog.setVisible(true);
            }
        });

        addTopPanelButton("Recharge Credit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RechargeCreditDialog rechargeCreditDialog = new RechargeCreditDialog(UserView.this);
                rechargeCreditDialog.setVisible(true);
            }
        });
    }


    public void refreshView() {
        updateVisualizerPanel(false);
    }
}