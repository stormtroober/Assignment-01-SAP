package sap.ass01.hexagonal.application.ports;


import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;

import java.util.List;
import java.util.Optional;

public interface EBikeRepository {
/**
 * Finds an e-bike by its ID.
 *
 * @param bikeId the ID of the e-bike to find.
 * @return an Optional containing the found e-bike, or an empty Optional if no e-bike is found.
 */
Optional<EBikeDTO> findEBikeById(String bikeId);

/**
 * Finds all e-bikes.
 *
 * @return a list of all e-bikes.
 */
List<EBikeDTO> findAllEBikes();

/**
 * Saves a new e-bike.
 *
 * @param eBike the e-bike to save.
 */
void saveEBike(EBikeDTO eBike);

/**
 * Updates an existing e-bike.
 *
 * @param eBike the e-bike to update.
 */
void updateEBike(EBikeDTO eBike);

/**
 * Cleans the repository, removing all e-bikes.
 */
void clean();
}
