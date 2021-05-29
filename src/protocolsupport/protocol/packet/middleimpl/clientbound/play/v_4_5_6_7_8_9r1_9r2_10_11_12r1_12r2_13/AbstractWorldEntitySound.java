package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySound;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;

public abstract class AbstractWorldEntitySound extends MiddleEntitySound {

	protected AbstractWorldEntitySound(MiddlePacketInit init) {
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
