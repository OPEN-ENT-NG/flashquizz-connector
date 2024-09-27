package fr.openent.flashquizz;

import fr.openent.flashquizz.controller.FlashquizzController;
import io.vertx.core.eventbus.EventBus;
import org.entcore.common.http.BaseServer;

public class Flashquizz extends BaseServer {

    public static final String VIEW_RIGHT = "flashquizz.view";

    @Override
    public void start() throws Exception {
        super.start();

        EventBus eb = getEventBus(vertx);

        addController(new FlashquizzController());
    }
}