package fr.openent.flashquizz;

import fr.openent.flashquizz.config.FlashquizzConfig;
import fr.openent.flashquizz.controller.FlashquizzController;
import fr.openent.flashquizz.service.ServiceFactory;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.entcore.common.http.BaseServer;
import org.entcore.common.neo4j.Neo4j;

public class SSOFlashquizz extends BaseServer implements Handler<Message<JsonObject>> {
    
    protected static final Logger log = LoggerFactory.getLogger(SSOFlashquizz.class);
    private ServiceFactory serviceFactory;
    private FlashquizzConfig flashquizzConfig;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        
        this.flashquizzConfig = new FlashquizzConfig(config);
        this.serviceFactory = new ServiceFactory(vertx, Neo4j.getInstance(), flashquizzConfig);
        
        addController(new FlashquizzController(serviceFactory));
        
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
                .onSuccess(ssoData -> {
                    log.info("[Flashquizz@SsoFlashquizz::handle] SSO data generated successfully for userId: " + userId);
                    message.reply(ssoData);
                })
                .onFailure(error -> {
                    log.error("[Flashquizz@SsoFlashquizz::handle] Failed to generate SSO data: " + error.getMessage());
                    message.fail(500, error.getMessage());
                });
    }
}