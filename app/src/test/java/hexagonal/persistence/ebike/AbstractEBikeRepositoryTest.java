package hexagonal.persistence.ebike;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.domain.model.EBikeState;
import sap.ass01.hexagonal.application.ports.EBikeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractEBikeRepositoryTest {

    protected EBikeRepository ebikeRepository;

    protected abstract EBikeRepository createRepository();

    @BeforeEach
    public void setUp() {
        ebikeRepository = createRepository();
    }

    @Test
    public void testSaveEBike() {
        EBikeDTO ebike = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        ebikeRepository.saveEBike(ebike);
        Optional<EBikeDTO> retrievedEBikeOptional = ebikeRepository.findEBikeById("1");
        assertTrue(retrievedEBikeOptional.isPresent(), "EBike should be found by ID");
        EBikeDTO retrievedEBike = retrievedEBikeOptional.get();
        assertEquals("1", retrievedEBike.id(), "EBike ID should match");
        assertEquals(EBikeState.AVAILABLE.name(), retrievedEBike.state(), "EBike state should match");
        assertEquals(10.0, retrievedEBike.x(), "EBike X coordinate should match");
        assertEquals(20.0, retrievedEBike.y(), "EBike Y coordinate should match");
        assertEquals(80, retrievedEBike.battery(), "EBike battery level should match");
    }

    @Test
    public void testSaveDuplicateEBike() {
        EBikeDTO ebike1 = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        ebikeRepository.saveEBike(ebike1);
        EBikeDTO ebike2 = new EBikeDTO("1", 15.0, 25.0, "MAINTENANCE", 90);
        ebikeRepository.saveEBike(ebike2);

        List<EBikeDTO> ebikes = ebikeRepository.findAllEBikes();
        assertEquals(1, ebikes.size(), "There should be only one ebike in the repository");
        assertEquals("1", ebikes.get(0).id(), "EBike ID should match");
        assertEquals(EBikeState.AVAILABLE.name(), ebikes.get(0).state(), "EBike state should match the first ebike");
        assertEquals(10.0, ebikes.get(0).x(), "EBike X coordinate should match the first ebike");
        assertEquals(20.0, ebikes.get(0).y(), "EBike Y coordinate should match the first ebike");
        assertEquals(80, ebikes.get(0).battery(), "EBike battery level should match the first ebike");
    }

    @Test
    public void testFindEBikeById() {
        EBikeDTO ebike = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        ebikeRepository.saveEBike(ebike);
        Optional<EBikeDTO> retrievedEBikeOptional = ebikeRepository.findEBikeById("1");
        assertTrue(retrievedEBikeOptional.isPresent(), "EBike should be found by ID");
        EBikeDTO retrievedEBike = retrievedEBikeOptional.get();
        assertEquals("1", retrievedEBike.id(), "EBike ID should match");
        assertEquals(EBikeState.AVAILABLE.name(), retrievedEBike.state(), "EBike state should match");
        assertEquals(10.0, retrievedEBike.x(), "EBike X coordinate should match");
        assertEquals(20.0, retrievedEBike.y(), "EBike Y coordinate should match");
        assertEquals(80, retrievedEBike.battery(), "EBike battery level should match");
    }

    @Test
    public void testFindAllEBikes() {
        EBikeDTO ebike1 = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        EBikeDTO ebike2 = new EBikeDTO("2", 15.0, 25.0, "MAINTENANCE", 90);
        ebikeRepository.saveEBike(ebike1);
        ebikeRepository.saveEBike(ebike2);

        List<EBikeDTO> ebikes = ebikeRepository.findAllEBikes();
        assertEquals(2, ebikes.size(), "There should be two ebikes in the repository");
        assertTrue(ebikes.stream().anyMatch(ebike -> ebike.id().equals("1")), "EBike with ID 1 should be present");
        assertTrue(ebikes.stream().anyMatch(ebike -> ebike.id().equals("2")), "EBike with ID 2 should be present");
    }

    @Test
    public void testUpdateEBike() {
        EBikeDTO ebike = new EBikeDTO("1", 10.0, 20.0, "AVAILABLE", 80);
        ebikeRepository.saveEBike(ebike);

        EBikeDTO updatedEBike = new EBikeDTO("1", 15.0, 25.0, "MAINTENANCE", 90);
        ebikeRepository.updateEBike(updatedEBike);

        Optional<EBikeDTO> retrievedEBikeOptional = ebikeRepository.findEBikeById("1");
        assertTrue(retrievedEBikeOptional.isPresent(), "EBike should be found by ID");
        EBikeDTO retrievedEBike = retrievedEBikeOptional.get();
        assertEquals("1", retrievedEBike.id(), "EBike ID should match");
        assertEquals(EBikeState.MAINTENANCE.name(), retrievedEBike.state(), "EBike state should match updated value");
        assertEquals(15.0, retrievedEBike.x(), "EBike X coordinate should match updated value");
        assertEquals(25.0, retrievedEBike.y(), "EBike Y coordinate should match updated value");
        assertEquals(90, retrievedEBike.battery(), "EBike battery level should match updated value");
    }
}