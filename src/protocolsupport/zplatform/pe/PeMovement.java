package protocolsupport.zplatform.pe;

import org.bukkit.plugin.java.JavaPlugin;

import gnu.trove.procedure.TObjectProcedure;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityTeleport;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.types.NetworkEntity;

public class PeMovement implements Runnable {

	private int taskId = -1;
	private final JavaPlugin protocolSupport;
	private Connection connection;
	private NetworkDataCache cache;
	
	public PeMovement(JavaPlugin protocolSupport, Connection connection, NetworkDataCache cache) {
		this.protocolSupport = protocolSupport;
		this.connection = connection;
		this.cache = cache;
	}
	
	@Override
	public void run() {
		//Runs every tick.
		if(connection != null && cache != null) {
			cache.getWatchedEntities().forEachValue(updateVelocity);
		} else {
			stop();
		}
	}
	
	TObjectProcedure<NetworkEntity> updateVelocity = new TObjectProcedure<NetworkEntity>() {
		@Override
		public boolean execute(NetworkEntity entity) {
			if(entity != null && entity.hasVelocity()) {
				entity.addPosition(entity.getVelocity().multiply(1/8000));
				sendMovementUpdate(entity);
			}
			return true;
		}
	};
	
	public void sendMovementUpdate(NetworkEntity entity) {
		connection.
		connection.sendPacket(EntityTeleport.create(entity, connection.getVersion()));
	}
	
	public void start() {
		taskId = protocolSupport.getServer().getScheduler().scheduleSyncRepeatingTask(protocolSupport, this, 0, 1);
	}
	
	public void stop() {
		protocolSupport.getServer().getScheduler().cancelTask(taskId);
	}

}
