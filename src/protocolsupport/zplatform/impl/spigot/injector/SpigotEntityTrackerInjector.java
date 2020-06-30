//package protocolsupport.zplatform.impl.spigot.injector;
//
//import java.lang.reflect.Field;
//import java.util.function.Supplier;
//
//import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.world.WorldInitEvent;
//
//import net.minecraft.server.v1_15_R1.PlayerChunkMap;
//import protocolsupport.ProtocolSupport;
//import protocolsupport.utils.ReflectionUtils;
//import protocolsupport.zplatform.impl.spigot.entitytracker.PaperSpigotEntityTrackerEntryInjectorMap;
//import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTrackerEntryInjectorMap;
//
//public class SpigotEntityTrackerInjector implements Listener {
//
//	protected final Field field;
//	protected final Supplier<Object> injectorMapSupplier;
//	public SpigotEntityTrackerInjector() {
//		Field lField = null;
//		Supplier<Object> lInjectorMapSupplier = null;
//		try {
//			lField = ReflectionUtils.getField(PlayerChunkMap.class, "trackedEntities");
//			switch (lField.getType().getName()) {
//				case "org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectMap": {
//					lInjectorMapSupplier = SpigotEntityTrackerEntryInjectorMap::new;
//					break;
//				}
//				case "it.unimi.dsi.fastutil.ints.Int2ObjectMap": {
//					lInjectorMapSupplier = PaperSpigotEntityTrackerEntryInjectorMap::new;
//					break;
//				}
//				default: {
//					throw new IllegalArgumentException("Can't find an entity tracker injector map for type " + lField.getType().getName());
//				}
//			}
//		} catch (Throwable e) {
//			ProtocolSupport.logError("Failed to create entity tracker entry injector", e);
//		}
//		this.field = lField;
//		this.injectorMapSupplier = lInjectorMapSupplier;
//	}
//
//	@EventHandler
//	public void onWorldInit(WorldInitEvent event) {
//		if (injectorMapSupplier == null) {
//			return;
//		}
//		PlayerChunkMap chunkmap = ((CraftWorld) event.getWorld()).getHandle().getChunkProvider().playerChunkMap;
//		try {
//			field.set(chunkmap, injectorMapSupplier.get());
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			ProtocolSupport.logError("Failed to inject entity tracker entry injector", e);
//		}
//	}
//
//}
