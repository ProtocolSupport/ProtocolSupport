package protocolsupport.zplatform.impl.spigot.injector;

import java.lang.reflect.Field;
import java.util.logging.Level;

import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_14_R1.PlayerChunkMap;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.impl.spigot.entitytracker.PaperSpigotEntityTrackerEntryInjectorMap;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTrackerEntryInjectorMap;

public class SpigotEntityTrackerInjector implements Listener {

	protected final Field field;
	protected final Object injectorMap;
	public SpigotEntityTrackerInjector() {
		Field lField = null;
		Object lInjectorMap = null;
		try {
			lField = ReflectionUtils.getField(PlayerChunkMap.class, "trackedEntities");
			switch (lField.getType().getName()) {
				case "org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectMap": {
					lInjectorMap = new SpigotEntityTrackerEntryInjectorMap();
					break;
				}
				case "it.unimi.dsi.fastutil.ints.Int2ObjectMap": {
					lInjectorMap = new PaperSpigotEntityTrackerEntryInjectorMap();
					break;
				}
				default: {
					throw new IllegalArgumentException("Can't find an entity tracker injector map for type " + lField.getType().getName());
				}
			}
		} catch (Throwable e) {
			ProtocolSupport.getInstance().getLogger().log(Level.SEVERE, e, () -> "Failed to create entity tracker entry injector");
		}
		this.field = lField;
		this.injectorMap = lInjectorMap;
	}

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		if (injectorMap == null) {
			return;
		}
		PlayerChunkMap chunkmap = ((CraftWorld) event.getWorld()).getHandle().getChunkProvider().playerChunkMap;
		try {
			field.set(chunkmap, injectorMap);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			ProtocolSupport.getInstance().getLogger().log(Level.SEVERE, e, () -> "Failed to inject entity tracker entry injector");
		}
	}

}
