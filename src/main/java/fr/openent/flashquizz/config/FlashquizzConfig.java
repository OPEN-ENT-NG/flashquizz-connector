package fr.openent.flashquizz.config;

import io.vertx.core.json.JsonObject;

public class FlashquizzConfig {
    private static final String EB_ADDRESS = "eb-address";
    private static final String DEFAULT_EB_ADDRESS = "fr.openent.ssoflashquizz";

    private final JsonObject config;

    public FlashquizzConfig(JsonObject config) {
        this.config = config;
    }

    public String ebAddress() {
        return config.getString(EB_ADDRESS, DEFAULT_EB_ADDRESS);
    }

    public JsonObject getConfig() {
        return config;
    }
}
