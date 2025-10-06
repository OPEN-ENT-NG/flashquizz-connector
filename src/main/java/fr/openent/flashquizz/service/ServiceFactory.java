package fr.openent.flashquizz.service;

import fr.openent.flashquizz.config.FlashquizzConfig;
import fr.openent.flashquizz.service.impl.DefaultFlashquizzSsoService;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.entcore.common.neo4j.Neo4j;

public class ServiceFactory {
    
    private final FlashquizzConfig config;
    private final EventBus eventBus;
    
    private FlashquizzSsoService flashquizzSsoService;

    public ServiceFactory(Vertx vertx, Neo4j neo4j, FlashquizzConfig config) {
        this.config = config;
        this.eventBus = vertx.eventBus();
    }

    public FlashquizzSsoService flashquizzSsoService() {
        if (flashquizzSsoService == null) {
            flashquizzSsoService = new DefaultFlashquizzSsoService(config);
        }
        return flashquizzSsoService;
    }

    public EventBus eventBus() {
        return eventBus;
    }

    public FlashquizzConfig config() {
        return config;
    }
}