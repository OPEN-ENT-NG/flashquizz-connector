package fr.openent.flashquizz;

import fr.openent.flashquizz.config.FlashquizzConfig;
import fr.openent.flashquizz.controller.FlashquizzController;
import fr.openent.flashquizz.service.ServiceFactory;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import org.entcore.common.http.BaseServer;
import org.entcore.common.neo4j.Neo4j;

public class FlashquizzConnector extends BaseServer {
    
    private ServiceFactory serviceFactory;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        final Promise<Void> promise = Promise.promise();
        super.start(promise);
        promise.future()
            .compose(e -> initFlashquizz())
            .onFailure(th -> log.error("[Flashquizz-Connector@FlashquizzConnector::start] Failed to start Flashquizz module.", th))
            .onComplete(startPromise);
    }

    public Future<Void> initFlashquizz() {
        FlashquizzConfig flashquizzConfig = new FlashquizzConfig(config);
        this.serviceFactory = new ServiceFactory(vertx, Neo4j.getInstance(), flashquizzConfig);
        return addController(new FlashquizzController(serviceFactory))
            .compose(e -> vertx.deployVerticle(SsoFlashquizz.class, new DeploymentOptions().setConfig(config)))
            .mapEmpty();
    }
}