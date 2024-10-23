package hexagonal.persistence.ride;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.application.ports.RideRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractRideRepositoryTest {

    protected RideRepository rideRepository;

    protected abstract RideRepository createRepository();

    @BeforeEach
    public void setUp() {
        rideRepository = createRepository();
    }

    @Test
    public void testSaveRide() {
        UserDTO user = new UserDTO("1", 100, false);
        EBikeDTO ebike = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        RideDTO ride = new RideDTO("1", ebike, user, null, Optional.empty());
        rideRepository.saveRide(ride);
        Optional<RideDTO> retrievedRideOptional = rideRepository.findRideById("1");
        assertTrue(retrievedRideOptional.isPresent(), "Ride should be found by ID");
        RideDTO retrievedRide = retrievedRideOptional.get();
        assertEquals("1", retrievedRide.id(), "Ride ID should match");
        assertEquals(user.id(), retrievedRide.user().id(), "User ID should match");
        assertEquals(ebike.id(), retrievedRide.ebike().id(), "EBike ID should match");
    }

    @Test
    public void testSaveDuplicateRide() {
        UserDTO user = new UserDTO("1", 100, false);
        EBikeDTO ebike = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        RideDTO ride1 = new RideDTO("1", ebike, user, null, Optional.empty());
        rideRepository.saveRide(ride1);
        RideDTO ride2 = new RideDTO("1", ebike, user, null, Optional.empty());
        rideRepository.saveRide(ride2);

        List<RideDTO> rides = rideRepository.findAllRides();
        assertEquals(1, rides.size(), "There should be only one ride in the repository");
        assertEquals("1", rides.get(0).id(), "Ride ID should match the first ride");
    }

    @Test
    public void testFindRideById() {
        UserDTO user = new UserDTO("1", 100, false);
        EBikeDTO ebike = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        RideDTO ride = new RideDTO("1", ebike, user, null, Optional.empty());
        rideRepository.saveRide(ride);
        Optional<RideDTO> retrievedRideOptional = rideRepository.findRideById("1");
        assertTrue(retrievedRideOptional.isPresent(), "Ride should be found by ID");
        RideDTO retrievedRide = retrievedRideOptional.get();
        assertEquals("1", retrievedRide.id(), "Ride ID should match");
        assertEquals(user.id(), retrievedRide.user().id(), "User ID should match");
        assertEquals(ebike.id(), retrievedRide.ebike().id(), "EBike ID should match");
    }

    @Test
    public void testFindAllRides() {
        UserDTO user1 = new UserDTO("1", 100, false);
        UserDTO user2 = new UserDTO("2", 200, true);
        EBikeDTO ebike1 = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        EBikeDTO ebike2 = new EBikeDTO("2", 15.0, 25.0, "MAINTENANCE", 90);
        RideDTO ride1 = new RideDTO("1", ebike1, user1, null, Optional.empty());
        RideDTO ride2 = new RideDTO("2", ebike2, user2, null, Optional.empty());
        rideRepository.saveRide(ride1);
        rideRepository.saveRide(ride2);

        List<RideDTO> rides = rideRepository.findAllRides();
        assertEquals(2, rides.size(), "There should be two rides in the repository");
        assertTrue(rides.stream().anyMatch(ride -> ride.id().equals("1")), "Ride with ID 1 should be present");
        assertTrue(rides.stream().anyMatch(ride -> ride.id().equals("2")), "Ride with ID 2 should be present");
    }

    @Test
    public void testUpdateRide() {
        UserDTO user = new UserDTO("1", 100, false);
        EBikeDTO ebike = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        RideDTO ride = new RideDTO("1", ebike, user, null, Optional.empty());
        rideRepository.saveRide(ride);

        RideDTO updatedRide = new RideDTO("1", ebike, user, null, Optional.empty());
        rideRepository.updateRide(updatedRide);

        Optional<RideDTO> retrievedRideOptional = rideRepository.findRideById("1");
        assertTrue(retrievedRideOptional.isPresent(), "Ride should be found by ID");
        RideDTO retrievedRide = retrievedRideOptional.get();
        assertEquals("1", retrievedRide.id(), "Ride ID should match");
    }
}