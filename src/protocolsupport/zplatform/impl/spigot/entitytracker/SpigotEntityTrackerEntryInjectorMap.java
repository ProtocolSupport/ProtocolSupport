package protocolsupport.zplatform.impl.spigot.entitytracker;

import java.util.logging.Level;

import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import net.minecraft.server.v1_14_R1.PlayerChunkMap.EntityTracker;
import protocolsupport.ProtocolSupport;

public class SpigotEntityTrackerEntryInjectorMap extends Int2ObjectOpenHashMap<EntityTracker> {

	private static final long serialVersionUID = 1L;

	@Override
	public EntityTracker put(int k, EntityTracker v) {
		try {
			SpigotEntityTrackerEntryInjector.injectEntry(v);
		} catch (Throwable e) {
			ProtocolSupport.getInstance().getLogger().log(Level.SEVERE, e, () -> "Failed to inject entity tracker instance");
		}
		return super.put(k, v);
	}

}
