package sap.ass01.layered.persistence.dto;

public class UserDTO {
    private final String id;
    private final int credit;
    private final String permission;

    public UserDTO(final String id, final int credit, final String permission) {
        this.id = id;
        this.credit = credit;
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public int getCredit() {
        return credit;
    }

    public String getPermission() {
        return permission;
    }
}

