package sap.ass01.layered.UI.Models;

import sap.ass01.layered.services.dto.UserDTO;

public class UserViewModel {
    private String id;
    private int credit;
    private boolean admin;

    public UserViewModel(UserDTO userDTO) {
        this.id = userDTO.id();
        this.credit = userDTO.credit();
        this.admin = userDTO.admin();
    }

    public String getId() {
        return id;
    }

    public int getCredit() {
        return credit;
    }

    public boolean isAdmin() {
        return admin;
    }
}