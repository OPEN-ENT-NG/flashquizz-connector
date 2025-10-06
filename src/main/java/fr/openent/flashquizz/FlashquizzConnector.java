package fr.openent.flashquizz;

import fr.openent.flashquizz.config.FlashquizzConfig;
import fr.openent.flashquizz.controller.FlashquizzController;
import fr.openent.flashquizz.service.ServiceFactory;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import org.entcore.common.http.BaseServer;
import org.entcore.common.neo4j.Neo4j;

public class FlashquizzConnector extends BaseServer {
    
    private ServiceFactory serviceFactory;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        
        FlashquizzConfig flashquizzConfig = new FlashquizzConfig(config);
        this.serviceFactory = new ServiceFactory(vertx, Neo4j.getInstance(), flashquizzConfig);
        
        addController(new FlashquizzController(serviceFactory));
        
        startPromise.tryComplete();
        startPromise.tryFail("[Flashquizz-Connector@FlashquizzConnector::start] Failed to start Flashquizz module.");
        
        vertx.deployVerticle(SsoFlashquizz.class, new DeploymentOptions().setConfig(config));
    }
}