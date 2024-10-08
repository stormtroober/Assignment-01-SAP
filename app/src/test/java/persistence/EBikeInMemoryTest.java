package persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sap.ass01.layered.persistence.DTO.EBikeDTO;
import sap.ass01.layered.persistence.repository.DatabaseType;
import sap.ass01.layered.persistence.repository.EBikeRepository;
import sap.ass01.layered.persistence.repository.factory.EBikeRepositoryFactory;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EBikeInMemoryTest {

    private EBikeRepository ebikeRepository;
    private File saveFile = null;

    @BeforeEach
    public void setUp() {
        ebikeRepository = EBikeRepositoryFactory.createRepository(DatabaseType.IN_MEMORY);
        saveFile = ebikeRepository.getSaveFile();
    }

    @AfterEach
    public void cleanDatabase() {
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }

    @Test
    public void testSaveEBike() {
        EBikeDTO ebike = new EBikeDTO("1", "ACTIVE", 10.0, 20.0, 1.0, 0.0, 50.0, 80);
        ebikeRepository.saveEBike(ebike);
        Optional<EBikeDTO> retrievedEBikeOptional = ebikeRepository.findEBikeById("1");
        assertTrue(retrievedEBikeOptional.isPresent(), "EBike should be found by ID");
        EBikeDTO retrievedEBike = retrievedEBikeOptional.get();
        assertEquals("1", retrievedEBike.getId(), "EBike ID should match");
        assertEquals("ACTIVE", retrievedEBike.getState(), "EBike state should match");
        assertEquals(10.0, retrievedEBike.getX(), "EBike X coordinate should match");
        assertEquals(20.0, retrievedEBike.getY(), "EBike Y coordinate should match");
        assertEquals(1.0, retrievedEBike.getDirectionX(), "EBike directionX should match");
        assertEquals(0.0, retrievedEBike.getDirectionY(), "EBike directionY should match");
        assertEquals(50.0, retrievedEBike.getSpeed(), "EBike speed should match");
        assertEquals(80, retrievedEBike.getBatteryLevel(), "EBike battery level should match");
    }

    @Test
    public void testSaveDuplicateEBike() {
        EBikeDTO ebike1 = new EBikeDTO("1", "ACTIVE", 10.0, 20.0, 1.0, 0.0, 50.0, 80);
        ebikeRepository.saveEBike(ebike1);
        EBikeDTO ebike2 = new EBikeDTO("1", "INACTIVE", 15.0, 25.0, 0.5, 0.5, 60.0, 90);
        ebikeRepository.saveEBike(ebike2);

        List<EBikeDTO> ebikes = ebikeRepository.findAllEBikes();
        assertEquals(1, ebikes.size(), "There should be only one ebike in the repository");
        assertEquals("1", ebikes.get(0).getId(), "EBike ID should match");
        assertEquals("ACTIVE", ebikes.get(0).getState(), "EBike state should match the first ebike");
        assertEquals(10.0, ebikes.get(0).getX(), "EBike X coordinate should match the first ebike");
        assertEquals(20.0, ebikes.get(0).getY(), "EBike Y coordinate should match the first ebike");
        assertEquals(1.0, ebikes.get(0).getDirectionX(), "EBike directionX should match the first ebike");
        assertEquals(0.0, ebikes.get(0).getDirectionY(), "EBike directionY should match the first ebike");
        assertEquals(50.0, ebikes.get(0).getSpeed(), "EBike speed should match the first ebike");
        assertEquals(80, ebikes.get(0).getBatteryLevel(), "EBike battery level should match the first ebike");
    }

    @Test
    public void testFindEBikeById() {
        EBikeDTO ebike = new EBikeDTO("1", "ACTIVE", 10.0, 20.0, 1.0, 0.0, 50.0, 80);
        ebikeRepository.saveEBike(ebike);
        Optional<EBikeDTO> retrievedEBikeOptional = ebikeRepository.findEBikeById("1");
        assertTrue(retrievedEBikeOptional.isPresent(), "EBike should be found by ID");
        EBikeDTO retrievedEBike = retrievedEBikeOptional.get();
        assertEquals("1", retrievedEBike.getId(), "EBike ID should match");
        assertEquals("ACTIVE", retrievedEBike.getState(), "EBike state should match");
        assertEquals(10.0, retrievedEBike.getX(), "EBike X coordinate should match");
        assertEquals(20.0, retrievedEBike.getY(), "EBike Y coordinate should match");
        assertEquals(1.0, retrievedEBike.getDirectionX(), "EBike directionX should match");
        assertEquals(0.0, retrievedEBike.getDirectionY(), "EBike directionY should match");
        assertEquals(50.0, retrievedEBike.getSpeed(), "EBike speed should match");
        assertEquals(80, retrievedEBike.getBatteryLevel(), "EBike battery level should match");
    }

    @Test
    public void testFindAllEBikes() {
        EBikeDTO ebike1 = new EBikeDTO("1", "ACTIVE", 10.0, 20.0, 1.0, 0.0, 50.0, 80);
        EBikeDTO ebike2 = new EBikeDTO("2", "INACTIVE", 15.0, 25.0, 0.5, 0.5, 60.0, 90);
        ebikeRepository.saveEBike(ebike1);
        ebikeRepository.saveEBike(ebike2);

        List<EBikeDTO> ebikes = ebikeRepository.findAllEBikes();
        assertEquals(2, ebikes.size(), "There should be two ebikes in the repository");
        assertTrue(ebikes.stream().anyMatch(ebike -> ebike.getId().equals("1")), "EBike with ID 1 should be present");
        assertTrue(ebikes.stream().anyMatch(ebike -> ebike.getId().equals("2")), "EBike with ID 2 should be present");
    }

    @Test
    public void testUpdateEBike() {
        EBikeDTO ebike = new EBikeDTO("1", "ACTIVE", 10.0, 20.0, 1.0, 0.0, 50.0, 80);
        ebikeRepository.saveEBike(ebike);

        EBikeDTO updatedEBike = new EBikeDTO("1", "INACTIVE", 15.0, 25.0, 0.5, 0.5, 60.0, 90);
        ebikeRepository.updateEBike(updatedEBike);

        Optional<EBikeDTO> retrievedEBikeOptional = ebikeRepository.findEBikeById("1");
        assertTrue(retrievedEBikeOptional.isPresent(), "EBike should be found by ID");
        EBikeDTO retrievedEBike = retrievedEBikeOptional.get();
        assertEquals("1", retrievedEBike.getId(), "EBike ID should match");
        assertEquals("INACTIVE", retrievedEBike.getState(), "EBike state should match updated value");
        assertEquals(15.0, retrievedEBike.getX(), "EBike X coordinate should match updated value");
        assertEquals(25.0, retrievedEBike.getY(), "EBike Y coordinate should match updated value");
        assertEquals(0.5, retrievedEBike.getDirectionX(), "EBike directionX should match updated value");
        assertEquals(0.5, retrievedEBike.getDirectionY(), "EBike directionY should match updated value");
        assertEquals(60.0, retrievedEBike.getSpeed(), "EBike speed should match updated value");
        assertEquals(90, retrievedEBike.getBatteryLevel(), "EBike battery level should match updated value");
    }
}