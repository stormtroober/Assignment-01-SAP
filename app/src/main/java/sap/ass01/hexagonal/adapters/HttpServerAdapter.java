package sap.ass01.hexagonal.adapters;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sap.ass01.hexagonal.domain.entities.EBike;
import sap.ass01.hexagonal.domain.services.EBikeService;

public class HttpServerAdapter extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(HttpServerAdapter.class);
    private final EBikeService ebikeService;
    private final static int DEFAULT_PORT = 8080;

    public HttpServerAdapter(EBikeService ebikeService) {
        this.ebikeService = ebikeService;
    }

    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.post("/api/ebikes").handler(this::registerNewEBike);
        router.get("/api/ebikes/:ebikeId").handler(this::getEBikeInfo);

        server.requestHandler(router).listen(DEFAULT_PORT);
        log.info("HTTP server started on port " + DEFAULT_PORT);
    }

    private void registerNewEBike(RoutingContext context) {
        JsonObject ebikeInfo = context.body().asJsonObject();
        String id = ebikeInfo.getString("id");
        String model = ebikeInfo.getString("model");

        ebikeService.registerEBike(id, model);

        JsonObject reply = new JsonObject().put("result", "ok");
        context.response().putHeader("content-type", "application/json").end(reply.toString());
    }

    private void getEBikeInfo(RoutingContext context) {
        String ebikeId = context.pathParam("ebikeId");
        EBike ebike = ebikeService.getEBikeInfo(ebikeId);

        JsonObject reply;
        if (ebike != null) {
            reply = new JsonObject().put("result", "ok").put("ebike", JsonObject.mapFrom(ebike));
        } else {
            reply = new JsonObject().put("result", "ebike-not-found");
        }
        context.response().putHeader("content-type", "application/json").end(reply.toString());
    }
}
