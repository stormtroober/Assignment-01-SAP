package sap.ass01.layered.persistence.repository;

import sap.ass01.layered.persistence.dto.EBikeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing the repository for managing e-bikes.
 */
public interface EBikeRepository {

    /**
     * Finds an e-bike by its ID.
     *
     * @param bikeId the ID of the e-bike to be found
     * @return an Optional containing the found e-bike, or empty if not found
     */
    Optional<EBikeDTO> findEBikeById(String bikeId);

    /**
     * Retrieves all e-bikes.
     *
     * @return a list of all e-bikes
     */
    List<EBikeDTO> findAllEBikes();

    /**
     * Saves the given e-bike to the repository.
     *
     * @param eBike the e-bike to be saved
     */
    void saveEBike(EBikeDTO eBike);

    /**
     * Updates the given e-bike in the repository.
     *
     * @param eBike the e-bike to be updated
     */
    void updateEBike(EBikeDTO eBike);

    /**
     * Cleans the database, removing all e-bikes.
     */
    void cleanDatabase();
}