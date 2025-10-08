package fr.openent.flashquizz.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;

public interface FlashquizzSsoService {

    /**
     * Generate complete SSO data for Flashquizz (user info + workflow rights)
     *
     * @param userId User identifier
     * @return Future with JsonObject containing all user data and workflow rights
     */
    Future<JsonArray> generateSsoData(String userId);
}
