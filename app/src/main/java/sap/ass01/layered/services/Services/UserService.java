package sap.ass01.layered.services.Services;

import sap.ass01.layered.services.dto.EBikeDTO;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface UserService extends RideService {
    CompletableFuture<Collection<EBikeDTO>> getAvailableEBikes();
}
