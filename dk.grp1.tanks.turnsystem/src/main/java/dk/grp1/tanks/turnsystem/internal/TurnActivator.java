package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class TurnActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println( "STARTING dk.grp1.tanks.player" );
        TurnPostProcessing turnPostProcessing = new TurnPostProcessing();
        bundleContext.registerService(IPostEntityProcessingService.class.getName(),turnPostProcessing ,null);
        bundleContext.registerService(IGamePluginService.class.getName(), new TurnGamePlugin(turnPostProcessing),null);


    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
