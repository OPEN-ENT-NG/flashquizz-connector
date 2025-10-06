package fr.openent.flashquizz.config;

import io.vertx.core.json.JsonObject;

public class FlashquizzConfig {
    private static final String EB_ADDRESS = "eb-address";
    private static final String DEFAULT_EB_ADDRESS = "fr.openent.ssoflashquizz";
    
    private static final String WORKFLOW_PREFIX = "workflow-prefix";
    private static final String DEFAULT_WORKFLOW_PREFIX = "fr.openent.flashquizz";
    
    private final JsonObject config;

    public FlashquizzConfig(JsonObject config) {
        this.config = config;
    }

    public String ebAddress() {
        return config.getString(EB_ADDRESS, DEFAULT_EB_ADDRESS);
    }

    public String workflowPrefix() {
        return config.getString(WORKFLOW_PREFIX, DEFAULT_WORKFLOW_PREFIX);
    }

    public JsonObject getConfig() {
        return config;
    }
}

