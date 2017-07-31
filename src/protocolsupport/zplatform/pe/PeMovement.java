package protocolsupport.zplatform.pe;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import gnu.trove.procedure.TObjectProcedure;
import protocolsupport.api.Connection;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.zplatform.ServerPlatform;

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
		//Runs every tick, stop when we cannot execute our functions anymore.
		if(connection != null && cache != null) {
			if(cache.getSelfPlayerEntityId() != -1) {
				System.out.println("Sending Faux Movement");
				cache.getWatchedEntities().forEachValue(updateVelocity);
			}
		} else {
			stop();
		}
	}
	
	TObjectProcedure<NetworkEntity> updateVelocity = new TObjectProcedure<NetworkEntity>() {
		@Override
		public boolean execute(NetworkEntity entity) {
			if(entity != null && entity.hasVelocity()) {
				updateMovement(entity, entity.getVelocity().multiply(1/8000));
			}
			return true;
		}
	};
	
	public void updateMovement(NetworkEntity entity, Vector relPos) {
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createRelEntityMove(entity.getId(), (int) relPos.getX(), (int) relPos.getY(), (int) relPos.getZ(), entity.getOnGround()));
	}
	
	public void start() {
		taskId = protocolSupport.getServer().getScheduler().scheduleSyncRepeatingTask(protocolSupport, this, 0, 1)/*.getTaskId()*/;
	}
	
	public void stop() {
		protocolSupport.getServer().getScheduler().cancelTask(taskId);
	}

}
