package fr.openent.flashquizz.model;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import static fr.openent.flashquizz.core.constants.Field.*;

public class WorkflowRights {
    
    private final boolean hasQuizzView;
    private final boolean hasQuizzGestion;
    private final boolean hasGameView;
    private final boolean hasGameGestion;

    public WorkflowRights(JsonArray rights) {
        if (rights == null) {
            rights = new JsonArray();
        }
        this.hasQuizzView = rights.contains(QUIZZ_VIEW);
        this.hasQuizzGestion = rights.contains(QUIZZ_GESTION);
        this.hasGameView = rights.contains(GAME_VIEW);
        this.hasGameGestion = rights.contains(GAME_GESTION);
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put(HAS_QUIZZ_VIEW, hasQuizzView)
                .put(HAS_QUIZZ_GESTION, hasQuizzGestion)
                .put(HAS_GAME_VIEW, hasGameView)
                .put(HAS_GAME_GESTION, hasGameGestion);
    }
}