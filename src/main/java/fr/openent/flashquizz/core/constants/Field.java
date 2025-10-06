package fr.openent.flashquizz.core.constants;

public class Field {
    // User fields
    public static final String USER_ID = "userId";
    public static final String LOGIN = "login";
    public static final String DISPLAY_NAME = "displayName";
    public static final String EMAIL = "email";
    public static final String PROFILE = "profile";
    public static final String ID = "id";

    // Workflow rights fields
    public static final String WORKFLOW_RIGHTS = "workflowRights";
    
    // Workflow rights names
    public static final String QUIZZ_VIEW = "quizz.view";
    public static final String QUIZZ_GESTION = "quizz.gestion";
    public static final String GAME_VIEW = "game.view";
    public static final String GAME_GESTION = "game.gestion";
    
    // Workflow rights boolean keys
    public static final String HAS_QUIZZ_VIEW = "hasQuizzView";
    public static final String HAS_QUIZZ_GESTION = "hasQuizzGestion";
    public static final String HAS_GAME_VIEW = "hasGameView";
    public static final String HAS_GAME_GESTION = "hasGameGestion";

    private Field() {
        throw new IllegalStateException("Utility class");
    }
}