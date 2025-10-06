package fr.openent.flashquizz.model;

import io.vertx.core.json.JsonObject;
import static fr.openent.flashquizz.core.constants.Field.*;

public class SsoData {

    private final String id;
    private final String login;
    private final String displayName;
    private final String email;
    private final String profile;
    private final WorkflowRights workflowRights;

    public SsoData(JsonObject neo4jResult) {
        this.id = neo4jResult.getString(ID);
        this.login = neo4jResult.getString(LOGIN);
        this.displayName = neo4jResult.getString(DISPLAY_NAME);
        this.email = neo4jResult.getString(EMAIL);
        this.profile = neo4jResult.getString(PROFILE);
        this.workflowRights = new WorkflowRights(neo4jResult.getJsonArray(WORKFLOW_RIGHTS));
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put(ID, id)
                .put(LOGIN, login)
                .put(DISPLAY_NAME, displayName)
                .put(EMAIL, email)
                .put(PROFILE, profile)
                .put(WORKFLOW_RIGHTS, workflowRights.toJson());
    }
}