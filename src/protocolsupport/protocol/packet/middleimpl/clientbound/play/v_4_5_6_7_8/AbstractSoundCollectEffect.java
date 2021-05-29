package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import java.util.concurrent.ThreadLocalRandom;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCollectEffect;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractSoundCollectEffect extends MiddleCollectEffect {

	protected AbstractSoundCollectEffect(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	@Override
	protected void write() {
		NetworkEntity entity = entityCache.getEntity(entityId);
		if (entity != null) {
			NetworkEntityDataCache ecache = entity.getDataCache();
			if (entity.getType() == NetworkEntityType.EXP_ORB) {
				writeCollectSound(
					"entity.experience_orb.pickup",
					ecache.getX(), ecache.getY(), ecache.getZ(),
					0.1F, ((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 0.35F) + 0.9F
				);
			} else {
				writeCollectSound(
					"entity.item.pickup",
					ecache.getX(), ecache.getY(), ecache.getZ(),
					0.2F, ((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 1.4F) + 2.0F
				);
			}
		}

		writeCollectEffect();
	}

	protected abstract void writeCollectEffect();

	protected abstract void writeCollectSound(String sound, double x, double y, double z, float volume, float pitch);

}
