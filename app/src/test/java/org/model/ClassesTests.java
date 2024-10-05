package org.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sap.ass01.layered.domain.*;

public class ClassesTests {

    @Test
    public void testUserId(){
        User user = new User("1");
        assertEquals("1", user.getId());
    }

    @Test
    public void testUserCredit(){
        User user = new User("1");
        assertEquals(0, user.getCredit());
    }

    @Test
    public void testToString(){
        User user = new User("1");
        String expected = "{ id: 1, credit: 0 }";
        assertEquals(expected, user.toString(), "toString should return a string representation of the user");
    }

    @Test
    public void testUserIdImmutability(){
        User user = new User("1");
        String initialId = user.getId();
        assertEquals(initialId, user.getId(), "User id should be immutable");
    }

    @Test
    public void testEBikeId(){
        EBike ebike = new EBike("1");
        assertEquals("1", ebike.getId());
    }

    @Test
    public void testEBikeStateChange() {
        EBike ebike = new EBike("1");
        ebike.updateState(EBike.EBikeState.IN_USE);
        assertSame(EBike.EBikeState.IN_USE, ebike.getState(), "EBike state should be IN_USE");
    }

    @Test
    public void testEBikeUpdateLocation() {
        EBike ebike = new EBike("1");
        P2d newLoc = new P2d(10, 20);
        ebike.updateLocation(newLoc);
        assertNotNull(ebike.getLocation(), "Location should not be null after update");
        assertEquals(newLoc, ebike.getLocation(), "Location should be updated to the new location");
    }

    @Test
    public void testEBikeUpdateDirection() {
        EBike ebike = new EBike("1");
        V2d newDirection = new V2d(0, 1);
        ebike.updateDirection(newDirection);
        assertNotNull(ebike.getDirection(), "Direction should not be null after update");
        assertEquals(newDirection, ebike.getDirection(), "Direction should be updated to the new direction");
    }

    @Test
    public void testUserCorrespondsToEBikeDuringTheRide() {
        User user = new User("1");
        EBike ebike = new EBike("1");
        Ride ride = new Ride("1", user, ebike);
        assertSame(user, ride.getUser(), "The user should correspond to the user that took the bike");
        assertSame(ebike, ride.getEBike(), "The EBike should correspond to the bike that was taken");
    }

    @Test
    public void testRideStartDate() {
        User user = new User("1");
        EBike ebike = new EBike("1");
        Ride ride = new Ride("1", user, ebike);
        assertNotNull(ride.getStartedDate(), "The start date should not be null");
    }

    @Test
    public void testRideEndDateInitiallyEmpty() {
        User user = new User("1");
        EBike ebike = new EBike("1");
        Ride ride = new Ride("1", user, ebike);
        assertTrue(ride.getEndDate().isEmpty(), "The end date should be empty initially");
    }


}