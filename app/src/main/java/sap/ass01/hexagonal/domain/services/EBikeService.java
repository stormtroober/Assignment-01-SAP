package sap.ass01.hexagonal.domain.services;

import sap.ass01.hexagonal.domain.entities.EBike;
import sap.ass01.hexagonal.domain.repositories.EBikeRepository;
import sap.ass01.hexagonal.ports.EBikeUseCases;

import java.util.HashMap;
import java.util.Map;

public class EBikeService implements EBikeUseCases {
    //dummy implementation of a repository with an hashMap
    //It should be done with a database
    private EBikeRepository repository;

    public EBikeService(EBikeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerEBike(String id, String model) {
        //ebikeRepository.put(id, new EBike(id, model));
        repository.saveEBike(new EBike(id, model));
    }

    @Override
    public EBike getEBikeInfo(String ebikeId) {
        return repository.findEBikeById(ebikeId).orElse(null);
    }
}
