package fr.openent.flashquizz.core.constants;

public class Rights {

    public static final String DEFAULT_WORKFLOW_PREFIX = "fr.openent.flashquizz";
    public static final String QUIZZ_GESTION = "quizz.gestion";
    public static final String QUIZZ_VIEW = "quizz.view";
    public static final String GAME_GESTION = "game.gestion";
    public static final String GAME_VIEW = "game.view";

    private Rights() {
        throw new IllegalStateException("Utility class");
    }
}
