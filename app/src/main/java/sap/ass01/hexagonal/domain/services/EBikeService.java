package sap.ass01.hexagonal.domain.services;

import sap.ass01.hexagonal.domain.entities.EBike;
import sap.ass01.hexagonal.domain.repositories.EBikeRepository;
import sap.ass01.hexagonal.ports.EBikeUseCases;

import java.util.HashMap;
import java.util.Map;

public class EBikeService implements EBikeUseCases {
    private EBikeRepository repository;

    public EBikeService(EBikeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerEBike(String id, String model) {
        repository.saveEBike(new EBike(id, model));
    }

    @Override
    public EBike getEBikeInfo(String ebikeId) {
        return repository.findEBikeById(ebikeId).orElse(null);
    }
}
