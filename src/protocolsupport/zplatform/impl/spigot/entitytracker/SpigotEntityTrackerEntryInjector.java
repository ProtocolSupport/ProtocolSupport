package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.function.BiFunction;

import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.EntityTrackerEntry;
import net.minecraft.server.v1_15_R1.PlayerChunkMap.EntityTracker;
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

	public static void injectEntry(EntityTracker tracker, BiFunction<EntityTracker, Entity, EntityTrackerEntry> newEntryFunc) throws Throwable {
		setTrackerEntryField.invokeExact(tracker, newEntryFunc.apply(tracker, (Entity) getEntityField.invokeExact(tracker)));
	}

}
