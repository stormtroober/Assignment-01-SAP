package sap.ass01.hexagonal;

import io.vertx.core.Vertx;
import sap.ass01.hexagonal.adapters.HttpServerAdapter;
import sap.ass01.hexagonal.domain.services.EBikeService;

public class EBikeHexagonalBackend {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        EBikeService ebikeService = new EBikeService();
        HttpServerAdapter httpServerAdapter = new HttpServerAdapter(ebikeService);
        vertx.deployVerticle(httpServerAdapter);
    }
}
