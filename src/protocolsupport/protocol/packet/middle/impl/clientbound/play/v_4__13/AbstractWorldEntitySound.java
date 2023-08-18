package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntitySound;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;

public abstract class AbstractWorldEntitySound extends MiddleEntitySound {

	protected AbstractWorldEntitySound(IMiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	@Override
	protected void write() {
		String soundName = MinecraftSoundData.getNameById(id);
		if (soundName == null) {
			return;
		}
		NetworkEntity entity = entityCache.getEntity(entityId);
		if (entity == null) {
			return;
		}
		NetworkEntityDataCache ecache = entity.getDataCache();
		writeWorldSound(soundName, ecache.getX(), ecache.getY(), ecache.getZ());
	}

	protected abstract void writeWorldSound(String sound, double x, double y, double z);

}
