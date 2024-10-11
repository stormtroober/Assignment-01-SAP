package org.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sap.ass01.layered.domain.model.*;

public class ClassesTests {

    private User user;
    private EBike ebike;

    @BeforeEach
    public void setup() {
        user = new User("1", User.UserType.USER);
        ebike = new EBike("1", 0, 0);
    }

    @Test
    public void testUserId() {
        assertEquals("1", user.getId());
    }

    @Test
    public void testUserCredit() {
        assertEquals(0, user.getCredit());
    }

    @Test
    public void testToString() {
        String expected = "{ id: 1, credit: 0 }";
        assertEquals(expected, user.toString(), "toString should return a string representation of the user");
    }

    @Test
    public void testUserIdImmutability() {
        String initialId = user.getId();
        assertEquals(initialId, user.getId(), "User id should be immutable");
    }

    @Test
    public void testEBikeId() {
        assertEquals("1", ebike.getId());
    }

    @Test
    public void testEBikeStateChange() {
        ebike.setState(EBike.EBikeState.IN_USE);
        assertSame(EBike.EBikeState.IN_USE, ebike.getState(), "EBike state should be IN_USE");
    }

    @Test
    public void testEBikeUpdateLocation() {
        P2d newLoc = new P2d(10, 20);
        ebike.setLocation(newLoc);
        assertNotNull(ebike.getLocation(), "Location should not be null after update");
        assertEquals(newLoc, ebike.getLocation(), "Location should be updated to the new location");
    }

    @Test
    public void testEBikeUpdateDirection() {
        V2d newDirection = new V2d(0, 1);
        ebike.setDirection(newDirection);
        assertNotNull(ebike.getDirection(), "Direction should not be null after update");
        assertEquals(newDirection, ebike.getDirection(), "Direction should be updated to the new direction");
    }

    @Test
    public void testUserCorrespondsToEBikeDuringTheRide() {
        Ride ride = new Ride("1", user, ebike);
        assertSame(user, ride.getUser(), "The user should correspond to the user that took the bike");
        assertSame(ebike, ride.getEbike(), "The EBike should correspond to the bike that was taken");
    }

    @Test
    public void testRideStartDate() {
        Ride ride = new Ride("1", user, ebike);
        assertNotNull(ride.getStartTime(), "The start date should not be null");
    }

    @Test
    public void testRideEndDateInitiallyEmpty() {
        Ride ride = new Ride("1", user, ebike);
        assertTrue(ride.getEndTime().isEmpty(), "The end date should be empty initially");
    }
}