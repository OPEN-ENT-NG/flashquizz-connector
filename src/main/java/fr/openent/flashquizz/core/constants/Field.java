package fr.openent.flashquizz.core.constants;

public class Field {
    // User fields
    public static final String USER_ID = "userId";
    public static final String LOGIN = "login";
    public static final String DISPLAY_NAME = "displayName";
    public static final String EMAIL = "email";
    public static final String PROFILE = "profile";

    // SSO fields
    public static final String HOST = "host";
    public static final String SERVICE_PROVIDER_ID = "serviceProviderId";
    public static final String GROUP = "group";

    // Structure fields
    public static final String STRUCTURES = "structures";
    public static final String CLASSES = "classes";
    public static final String ID = "id";
    public static final String NAME = "name";

    // Profile types
    public static final String TEACHER = "Teacher";
    public static final String STUDENT = "Student";
    public static final String PERSONNEL = "Personnel";
    public static final String RELATIVE = "Relative";

    // Workflow fields
    public static final String WORKFLOW_RIGHTS = "workflowRights";
    public static final String WORKFLOW_ID = "workflowId";
    public static final String RIGHTS = "rights";
    public static final String SCOPE = "scope";

    private Field() {
        throw new IllegalStateException("Utility class");
    }
}