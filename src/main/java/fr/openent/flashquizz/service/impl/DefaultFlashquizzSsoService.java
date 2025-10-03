package fr.openent.flashquizz.service.impl;

import fr.openent.flashquizz.config.FlashquizzConfig;
import fr.openent.flashquizz.service.FlashquizzSsoService;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.entcore.common.neo4j.Neo4j;
import org.entcore.common.neo4j.Neo4jResult;

import static fr.openent.flashquizz.core.constants.Field.*;

public class DefaultFlashquizzSsoService implements FlashquizzSsoService {

    protected static final Logger log = LoggerFactory.getLogger(DefaultFlashquizzSsoService.class);
    private final FlashquizzConfig config;

    public DefaultFlashquizzSsoService(FlashquizzConfig config) {
        this.config = config;
    }

    @Override
    public Future<JsonObject> generateSsoData(String userId) {
        Promise<JsonObject> promise = Promise.promise();

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
                .put("workflowPrefix", config.workflowPrefix());
        Neo4j.getInstance().execute(query, params, Neo4jResult.validUniqueResultHandler(result -> {
            if (result.isLeft()) {
                String error = String.format(
                        "[Flashquizz@DefaultFlashquizzSsoService::generateSsoData] Failed to get SSO data for userId %s: %s",
                        userId, result.left().getValue());
                log.error(error);
                promise.fail(result.left().getValue());
                return;
            }

            JsonObject ssoData = result.right().getValue();
            promise.complete(ssoData);
        }));

        return promise.future();
    }
}