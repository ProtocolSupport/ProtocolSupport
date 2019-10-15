package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityTrackerEntry;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.PlayerChunkMap.EntityTracker;
import net.minecraft.server.v1_14_R1.WorldServer;
import protocolsupport.utils.ReflectionUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class SpigotEntityTrackerEntryInjector {

	private static final MethodHandle setTrackerEntryField = createSetTrackerEntryFieldMH();
	private static final MethodHandle getEntityField = createGetEntityFieldMH();

	private static final MethodHandle createSetTrackerEntryFieldMH() {
		try {
			return MethodHandles.lookup().unreflectSetter(ReflectionUtils.getField(EntityTracker.class, "trackerEntry"));
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to create set tracker entry field method handle", e);
		}
	}

	private static final MethodHandle createGetEntityFieldMH() {
		try {
			return MethodHandles.lookup().unreflectGetter(ReflectionUtils.getField(EntityTracker.class, "tracker"));
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to create set tracker entry field method handle", e);
		}
	}

	public static void injectEntry(EntityTracker tracker) throws Throwable {
		Entity entity = (Entity) getEntityField.invokeExact(tracker);
		EntityTypes<?> entitytypes = entity.getEntityType();
		EntityTrackerEntry entry = new SpigotEntityTrackerEntry(
			(WorldServer) entity.world, entity,
			entitytypes.getUpdateInterval(),
			entitytypes.isDeltaTracking(),
			tracker::broadcast,
			tracker.trackedPlayers
		);
		setTrackerEntryField.invokeExact(tracker, entry);
	}

}
