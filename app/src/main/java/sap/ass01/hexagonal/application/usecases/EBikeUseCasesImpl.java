package sap.ass01.hexagonal.application.usecases;

import sap.ass01.hexagonal.application.entities.EBikeDTO;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.application.ports.EBikeUseCases;
import sap.ass01.hexagonal.domain.model.EBike;

import java.util.List;
import java.util.Optional;

public class EBikeUseCasesImpl implements EBikeUseCases {
    private final EBikeRepository ebikeRepository;

    public EBikeUseCasesImpl(EBikeRepository ebikeRepository) {
        this.ebikeRepository = ebikeRepository;
    }

    @Override
    public void registerEBike(EBikeDTO ebikeDTO) {
        ebikeRepository.saveEBike(ebikeDTO);
    }

    @Override
    public Optional<EBikeDTO> findEBikeById(String id) {
        return ebikeRepository.findEBikeById(id);
    }

    @Override
    public List<EBikeDTO> getAllEBikes() {
        return ebikeRepository.findAllEBikes();
    }
}
