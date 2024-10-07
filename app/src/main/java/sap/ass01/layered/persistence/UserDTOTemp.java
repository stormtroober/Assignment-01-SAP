package sap.ass01.layered.persistence;

public class UserDTOTemp {
    private String id;
    private int credit;

    public UserDTOTemp(String id, int credit) {
        this.id = id;
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public int getCredit() {
        return credit;
    }
}
