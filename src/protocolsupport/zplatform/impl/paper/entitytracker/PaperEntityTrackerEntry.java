package protocolsupport.zplatform.impl.paper.entitytracker;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityTrackerEntry;
import protocolsupport.api.ProtocolType;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.impl.spigot.entitytracker.SpigotEntityTrackerEntry;

import java.lang.reflect.Field;

public class PaperEntityTrackerEntry extends SpigotEntityTrackerEntry {
	public PaperEntityTrackerEntry(Entity entity, int trackRange, int viewDistance, int updateInterval, boolean updateVelocity) {
		super(entity, trackRange, viewDistance, updateInterval, updateVelocity);
		setEntityTracker(entity, this); // Paper
	}

	@Override
	public void addTrackedPlayer(EntityPlayer player) {
		this.trackedPlayerMap.put(player, true);
		ConnectionImpl connection = ConnectionImpl.getFromChannel(player.playerConnection.networkManager.channel);
		if ((connection != null) && (connection.getVersion().getProtocolType() == ProtocolType.PE)) {
			this.trackedPEPlayers.add(player);
		} else {
			this.trackedDefaultPlayers.add(player);
		}
	}

	@Override
	public void removeTrackedPlayer(EntityPlayer player) {
		this.trackedPlayerMap.remove(player);
		this.trackedDefaultPlayers.remove(player);
		this.trackedPEPlayers.remove(player);
	}

	public static void setEntityTracker(Entity entity, EntityTrackerEntry tracker) {
		Field field = ReflectionUtils.findUnderlying(entity.getClass(), "tracker");
		try {
			field.setAccessible(true);
			field.set(entity, tracker);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}