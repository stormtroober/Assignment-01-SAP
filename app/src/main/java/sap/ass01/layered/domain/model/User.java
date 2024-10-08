package sap.ass01.layered.domain.model;

public class User {


    private String id;
    private int credit;
    public enum UserType {
        USER, ADMIN
    }
    private UserType permission;

    public User(String id, UserType permission) {
        this.id = id;
        this.credit = 0;
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public int getCredit() {
        return credit;
    }

    public UserType getPermission() {
        return permission;
    }

    public void rechargeCredit(int deltaCredit) {
        credit += deltaCredit;
    }

    public void decreaseCredit(int amount) {
        credit -= amount;
        if (credit < 0) {
            credit = 0;
        }
    }

    public String toString() {
        return "{ id: " + id + ", credit: " + credit + " }";
    }
}
