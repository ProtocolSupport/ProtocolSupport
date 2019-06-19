//package protocolsupport.zplatform.impl.spigot.entitytracker;
//
//import java.lang.invoke.MethodHandle;
//import java.lang.invoke.MethodHandles;
//import java.util.logging.Level;
//
//import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
//import net.minecraft.server.v1_14_R1.Entity;
//import net.minecraft.server.v1_14_R1.EntityTrackerEntry;
//import net.minecraft.server.v1_14_R1.EntityTypes;
//import net.minecraft.server.v1_14_R1.PlayerChunkMap.EntityTracker;
//import net.minecraft.server.v1_14_R1.WorldServer;
//import protocolsupport.ProtocolSupport;
//import protocolsupport.utils.ReflectionUtils;
//import protocolsupportbuildprocessor.Preload;
//
//@Preload
//public class SpigotEntityTrackerEntryInjectorMap extends Int2ObjectOpenHashMap<EntityTracker> {
//
//	private static final MethodHandle setTrackerEntryField = createSetTrackerEntryFieldMH();
//	private static final MethodHandle getEntityField = createGetEntityFieldMH();
//
//	private static final MethodHandle createSetTrackerEntryFieldMH() {
//		try {
//			return MethodHandles.lookup().unreflectSetter(ReflectionUtils.getField(EntityTracker.class, "trackerEntry"));
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException("Unable to create set tracker entry field method handle", e);
//		}
//	}
//
//	private static final MethodHandle createGetEntityFieldMH() {
//		try {
//			return MethodHandles.lookup().unreflectGetter(ReflectionUtils.getField(EntityTracker.class, "tracker"));
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException("Unable to create set tracker entry field method handle", e);
//		}
//	}
//
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public EntityTracker put(int k, EntityTracker v) {
//		try {
//			Entity entity = (Entity) getEntityField.invokeExact(v);
//			EntityTypes<?> entitytypes = entity.getEntityType();
//			EntityTrackerEntry entry = new SpigotEntityTrackerEntry(
//				(WorldServer) entity.world, entity,
//				entitytypes.getUpdateInterval(),
//				entitytypes.isDeltaTracking(),
//				v::broadcast,
//				v.trackedPlayers
//			);
//			setTrackerEntryField.invokeExact(v, entry);
//		} catch (Throwable e) {
//			ProtocolSupport.getInstance().getLogger().log(Level.SEVERE, e, () -> "Failed to inject entity tracker instance");
//		}
//		return super.put(k, v);
//	}
//
//}
