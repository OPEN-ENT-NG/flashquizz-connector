package fr.openent.flashquizz.service.impl;

import fr.openent.flashquizz.config.FlashquizzConfig;
import fr.openent.flashquizz.model.SsoData;
import fr.openent.flashquizz.service.FlashquizzSsoService;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import org.entcore.common.neo4j.Neo4j;
import org.entcore.common.neo4j.Neo4jResult;

import static fr.openent.flashquizz.core.constants.Field.*;
import static fr.openent.flashquizz.core.constants.Rights.DEFAULT_WORKFLOW_PREFIX;

public class DefaultFlashquizzSsoService implements FlashquizzSsoService {

    protected static final Logger log = LoggerFactory.getLogger(DefaultFlashquizzSsoService.class);
    private final FlashquizzConfig config;

    public DefaultFlashquizzSsoService(FlashquizzConfig config) {
        this.config = config;
    }

    @Override
    public Future<JsonArray> generateSsoData(String userId) {
        Promise<JsonArray> promise = Promise.promise();

        String query = "MATCH (u:User {id: {userId}}) " +
                "OPTIONAL MATCH (u)-[:IN]->(g:Group)-[:AUTHORIZED]->(r:Role)-[:AUTHORIZE]->(wa:WorkflowAction) " +
                "WHERE wa.name STARTS WITH {workflowPrefix} " +
                "RETURN u.id as id, " +
                "u.login as login, " +
                "u.displayName as displayName, " +
                "COALESCE(u.emailInternal, u.email) as email, " +
                "HEAD(u.profiles) as profile, " +
                "COLLECT(DISTINCT wa.displayName) AS workflowRights";

        JsonObject params = new JsonObject()
                .put(USER_ID, userId)
                .put(WORKFLOW_PREFIX, DEFAULT_WORKFLOW_PREFIX);

        Neo4j.getInstance().execute(query, params, Neo4jResult.validUniqueResultHandler(result -> {
            if (result.isLeft()) {
                log.error(String.format(
                        "[Flashquizz@DefaultFlashquizzSsoService::generateSsoData] Failed to get SSO data for userId %s: %s",
                        userId, result.left().getValue()));
                promise.fail(result.left().getValue());
                return;
            }

            JsonArray attributes = buildSamlAttributes(result.right().getValue());
            promise.complete(attributes);
        }));

        return promise.future();
    }

    private JsonArray buildSamlAttributes(JsonObject userData) {
        JsonArray attributes = new JsonArray();

        // Add basic user attributes
        attributes.add(new JsonObject().put(ID, userData.getString(ID)));
        attributes.add(new JsonObject().put(LOGIN, userData.getString(LOGIN)));
        attributes.add(new JsonObject().put(DISPLAY_NAME, userData.getString(DISPLAY_NAME)));
        attributes.add(new JsonObject().put(EMAIL, userData.getString(EMAIL)));
        attributes.add(new JsonObject().put(PROFILE, userData.getString(PROFILE)));

        // Extract workflowRights from Neo4j result
        JsonArray workflowRightsArray = userData.getJsonArray(WORKFLOW_RIGHTS, new JsonArray());

        // Build SAML attribute names with prefix
        final String WR_PREFIX = WORKFLOW_RIGHTS + ".";

        // Add workflowRights attributes as booleans
        attributes.add(new JsonObject().put(
                WR_PREFIX + HAS_QUIZZ_VIEW,
                String.valueOf(workflowRightsArray.contains(QUIZZ_VIEW))));
        attributes.add(new JsonObject().put(
                WR_PREFIX + HAS_QUIZZ_GESTION,
                String.valueOf(workflowRightsArray.contains(QUIZZ_GESTION))));
        attributes.add(new JsonObject().put(
                WR_PREFIX + HAS_GAME_VIEW,
                String.valueOf(workflowRightsArray.contains(GAME_VIEW))));
        attributes.add(new JsonObject().put(
                WR_PREFIX + HAS_GAME_GESTION,
                String.valueOf(workflowRightsArray.contains(GAME_GESTION))));

        return attributes;
    }
}