package fr.openent.flashquizz.controller;

import fr.openent.flashquizz.Flashquizz;
import fr.wseduc.rs.ApiDoc;
import fr.wseduc.rs.Get;
import fr.wseduc.webutils.security.SecuredAction;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.entcore.common.controller.ControllerHelper;
import org.entcore.common.events.EventStore;
import org.entcore.common.events.EventStoreFactory;
import org.vertx.java.core.http.RouteMatcher;

import java.util.Map;

public class FlashquizzController extends ControllerHelper {

    private EventStore eventStore;

    private enum FlashquizzEvent { ACCESS }

    public FlashquizzController() {
        super();
    }

    @Override
    public void init(Vertx vertx, JsonObject config, RouteMatcher rm, Map<String, SecuredAction> securedActions) {
        super.init(vertx, config, rm, securedActions);
        eventStore = EventStoreFactory.getFactory().getEventStore(Flashquizz.class.getSimpleName());
    }

    @Get("")
    @ApiDoc("Render flashquizz view")
    @fr.wseduc.security.SecuredAction(Flashquizz.VIEW_RIGHT)
    public void view(HttpServerRequest request) {
        renderView(request);
        eventStore.createAndStoreEvent(FlashquizzEvent.ACCESS.name(), request);
    }
}
