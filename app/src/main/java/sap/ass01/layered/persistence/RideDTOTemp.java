package sap.ass01.layered.persistence;

import sap.ass01.layered.domain.EBike;
import sap.ass01.layered.domain.User;

import java.util.Date;
import java.util.Optional;

public class RideDTOTemp {
    private Date startedDate;
    private Optional<Date> endDate;
//    private User user;
//    private EBike ebike;
    //private boolean ongoing;
    private String id;

    public RideDTOTemp(Date startedDate, Optional<Date> endDate
//            , User user
            , String id
//            , EBike ebike
    ) {
        this.startedDate = startedDate;
        this.endDate = endDate;
//        this.user = user;
        this.id = id;
//        this.ebike = ebike;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public Optional<Date> getEndDate() {
        return endDate;
    }

//    public User getUser() {
//        return user;
//    }

//    public EBike getEbike() {
//        return ebike;
//    }

    public String getId() {
        return id;
    }
}
