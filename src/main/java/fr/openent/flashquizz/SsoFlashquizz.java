package fr.openent.flashquizz;

import fr.openent.flashquizz.config.FlashquizzConfig;
import fr.openent.flashquizz.service.ServiceFactory;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.entcore.common.neo4j.Neo4j;
import org.vertx.java.busmods.BusModBase;

public class SsoFlashquizz extends BusModBase implements Handler<Message<JsonObject>> {
    
    protected static final Logger log = LoggerFactory.getLogger(SsoFlashquizz.class);
    private ServiceFactory serviceFactory;

    @Override
    public void start() {
        super.start();
        FlashquizzConfig flashquizzConfig = new FlashquizzConfig(config);
        this.serviceFactory = new ServiceFactory(vertx, Neo4j.getInstance(), flashquizzConfig);
        vertx.eventBus().localConsumer(flashquizzConfig.ebAddress(), this);
        log.info("[Flashquizz] SSO service started on EventBus address: " + flashquizzConfig.ebAddress());
    }

    @Override
    public void handle(Message<JsonObject> message) {
        String userId = message.body().getString("userId");
        
        if (userId == null || userId.isEmpty()) {
            log.error("[Flashquizz@SsoFlashquizz::handle] Missing userId in message");
            message.fail(400, "Missing userId");
            return;
        }
        
        log.info("[Flashquizz@SsoFlashquizz::handle] Generating SSO data for userId: " + userId);
        
        this.serviceFactory.flashquizzSsoService().generateSsoData(userId)
                .onSuccess(message::reply)
                .onFailure(error -> {
                    log.error("[Flashquizz@SsoFlashquizz::handle] Failed to generate SSO data: " + error.getMessage());
                    message.fail(500, error.getMessage());
                });
    }
}