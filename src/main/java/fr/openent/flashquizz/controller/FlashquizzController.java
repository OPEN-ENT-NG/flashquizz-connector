package fr.openent.flashquizz.controller;

import fr.openent.flashquizz.service.ServiceFactory;
import fr.wseduc.rs.ApiDoc;
import fr.wseduc.rs.Get;
import fr.wseduc.security.ActionType;
import fr.wseduc.security.SecuredAction;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import org.entcore.common.controller.ControllerHelper;
import org.entcore.common.http.filter.ResourceFilter;
import org.entcore.common.http.filter.SuperAdminFilter;
import org.entcore.common.user.UserUtils;

import fr.openent.flashquizz.core.constants.Rights;

public class FlashquizzController extends ControllerHelper {

    private final ServiceFactory serviceFactory;

    public FlashquizzController(ServiceFactory serviceFactory) {
        super();
        this.serviceFactory = serviceFactory;
    }

    //init classic rights
    @SecuredAction(Rights.QUIZZ_GESTION)
    public void initQuizzGestionRight(final HttpServerRequest request) {
    }

    @SecuredAction(Rights.QUIZZ_VIEW)
    public void initQuizzViewRight(final HttpServerRequest request) {
    }

    @SecuredAction(Rights.GAME_GESTION)
    public void initGameGestionRight(final HttpServerRequest request) {
    }

    @SecuredAction(Rights.GAME_VIEW)
    public void initGameViewRight(final HttpServerRequest request) {
    }

    //health/test 
    @Get("")
    @ApiDoc("Flashquizz test route")
    @SecuredAction(value = "", type = ActionType.RESOURCE)
    @ResourceFilter(SuperAdminFilter.class)
    public void render(HttpServerRequest request) {
        JsonObject response = new JsonObject().put("message", "Flashquizz SSO Connector OK");
        renderJson(request, response);
    }

    @Get("/test-sso")
    @ApiDoc("Test SSO endpoint - calls EventBus service")
    @SecuredAction(value = "", type = ActionType.RESOURCE)
    @ResourceFilter(SuperAdminFilter.class)
    public void testSso(HttpServerRequest request) {
        UserUtils.getUserInfos(eb, request, user -> {
            if (user == null) {
                log.error("[Flashquizz@FlashquizzController::testSso] User not found");
                unauthorized(request);
                return;
            }

            String userId = user.getUserId();
            log.info("[Flashquizz@FlashquizzController::testSso] Testing SSO for userId: " + userId);

            JsonObject message = new JsonObject().put("userId", userId);

            eb.request(serviceFactory.config().ebAddress(), message, reply -> {
                if (reply.succeeded()) {
                    log.info("[Flashquizz@FlashquizzController::testSso] SSO data retrieved successfully");
                    render(request, (JsonArray) reply.result().body());
                } else {
                    log.error("[Flashquizz@FlashquizzController::testSso] Failed to get SSO data: "
                            + reply.cause().getMessage());
                    renderError(request);
                }
            });
        });
    }

    @Get("/sso-direct")
    @ApiDoc("Direct SSO endpoint - bypasses EventBus (for testing only)")
    @SecuredAction(value = "", type = ActionType.RESOURCE)
    @ResourceFilter(SuperAdminFilter.class)
    public void ssoDirect(HttpServerRequest request) {
        UserUtils.getUserInfos(eb, request, user -> {
            if (user == null) {
                log.error("[Flashquizz@FlashquizzController::ssoDirect] User not found");
                unauthorized(request);
                return;
            }

            String userId = user.getUserId();
            log.info("[Flashquizz@FlashquizzController::ssoDirect] Direct SSO call for userId: " + userId);

            serviceFactory.flashquizzSsoService().generateSsoData(userId)
                    .onSuccess(ssoData -> {
                        log.info("[Flashquizz@FlashquizzController::ssoDirect] SSO data retrieved successfully");
                        renderJson(request, ssoData);
                    })
                    .onFailure(error -> {
                        log.error("[Flashquizz@FlashquizzController::ssoDirect] Error generating SSO data: "
                                + error.getMessage());
                        renderError(request);
                    });
        });
    }
}