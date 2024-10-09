package sap.ass01.layered.services.Services;

import sap.ass01.layered.services.dto.EBikeDTO;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface AdminService {
    CompletableFuture<EBikeDTO> createEBike(String bikeId, int x, int y);
    CompletableFuture<Collection<EBikeDTO>> getAllEBikes();
}
