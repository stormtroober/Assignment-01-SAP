package sap.ass01.hexagonal;

import io.vertx.core.Vertx;
import sap.ass01.hexagonal.adapters.HttpServerAdapter;
import sap.ass01.hexagonal.adapters.infrastructure.db.DiskEBikeRepositoryImpl;
import sap.ass01.hexagonal.domain.repositories.EBikeRepository;
import sap.ass01.hexagonal.domain.services.EBikeService;

public class EBikeHexagonalBackend {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        EBikeRepository ebikeRepository = new DiskEBikeRepositoryImpl();
        EBikeService ebikeService = new EBikeService(ebikeRepository);
        HttpServerAdapter httpServerAdapter = new HttpServerAdapter(ebikeService);
        vertx.deployVerticle(httpServerAdapter);
    }
}