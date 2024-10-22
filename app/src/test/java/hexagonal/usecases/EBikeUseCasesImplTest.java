package hexagonal.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.EBikeUseCases;
import sap.ass01.hexagonal.application.usecases.EBikeUseCasesImpl;
import sap.ass01.hexagonal.infrastructure.adapters.infrastructure.persistence.DiskEBikeRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EBikeUseCasesImplTest {

    private EBikeUseCases ebikeUseCases;

    @BeforeEach
    void setUp() {
        DiskEBikeRepositoryImpl diskEBikeRepository = new DiskEBikeRepositoryImpl();
        ebikeUseCases = new EBikeUseCasesImpl(diskEBikeRepository);
    }

    @Test
    void testRegisterEBike() {
        EBikeDTO ebikeDTO = new EBikeDTO("1", 0.0, 0.0, "available", 100);

        ebikeUseCases.registerEBike(ebikeDTO);

        Optional<EBikeDTO> foundEBike = ebikeUseCases.findEBikeById("1");
        assertTrue(foundEBike.isPresent());
        assertEquals("1", foundEBike.get().id());
        assertEquals(0.0, foundEBike.get().x());
        assertEquals(0.0, foundEBike.get().y());
        assertEquals("available", foundEBike.get().state());
        assertEquals(100, foundEBike.get().battery());
    }

    @Test
    void testFindEBikeById() {
        EBikeDTO ebikeDTO = new EBikeDTO("1", 0.0, 0.0, "available", 100);
        ebikeUseCases.registerEBike(ebikeDTO);

        Optional<EBikeDTO> result = ebikeUseCases.findEBikeById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().id());
        assertEquals(0.0, result.get().x());
        assertEquals(0.0, result.get().y());
        assertEquals("available", result.get().state());
        assertEquals(100, result.get().battery());
    }

    @Test
    void testGetAllEBikes() {
        EBikeDTO ebike1 = new EBikeDTO("1", 0.0, 0.0, "available", 100);
        EBikeDTO ebike2 = new EBikeDTO("2", 1.0, 1.0, "in_use", 80);
        ebikeUseCases.registerEBike(ebike1);
        ebikeUseCases.registerEBike(ebike2);

        List<EBikeDTO> result = ebikeUseCases.getAllEBikes();

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).id());
        assertEquals(0.0, result.get(0).x());
        assertEquals(0.0, result.get(0).y());
        assertEquals("available", result.get(0).state());
        assertEquals(100, result.get(0).battery());

        assertEquals("2", result.get(1).id());
        assertEquals(1.0, result.get(1).x());
        assertEquals(1.0, result.get(1).y());
        assertEquals("in_use", result.get(1).state());
        assertEquals(80, result.get(1).battery());
    }
}