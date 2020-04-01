package protocolsupport.zplatform.impl.spigot.entitytracker;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.PlayerChunkMap.EntityTracker;
import net.minecraft.server.v1_15_R1.WorldServer;
import protocolsupport.ProtocolSupport;

public class PaperSpigotEntityTrackerEntryInjectorMap extends Int2ObjectOpenHashMap<EntityTracker> {

	private static final long serialVersionUID = 1L;

	@Override
	public EntityTracker put(int k, EntityTracker v) {
		try {
			SpigotEntityTrackerEntryInjector.injectEntry(v, (tracker, entity) -> {
				EntityTypes<?> entitytypes = entity.getEntityType();
				return new SpigotEntityTrackerEntry(
					(WorldServer) entity.world, entity,
					entitytypes.getUpdateInterval(),
					entitytypes.isDeltaTracking(),
					tracker::broadcast,
					tracker.trackedPlayerMap
				);
			});
		} catch (Throwable e) {
			ProtocolSupport.logError("Failed to inject entity tracker instance", e);
		}
		return super.put(k, v);
	}

}
