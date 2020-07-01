package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import java.util.concurrent.ThreadLocalRandom;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCollectEffect;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public abstract class AbstractSoundCollectEffect extends MiddleCollectEffect {

	public AbstractSoundCollectEffect(ConnectionImpl connection) {
		super(connection);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	@Override
	protected void writeToClient() {
		NetworkEntity entity = entityCache.getEntity(entityId);
		if (entity != null) {
			switch (entity.getType()) {
				case ITEM: {
					NetworkEntityDataCache ecache = entity.getDataCache();
					writeCollectSound(
						"entity.item.pickup",
						ecache.getX(), ecache.getY(), ecache.getZ(),
						0.2F, (((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 0.7F) + 1.0F) * 2.0F
					);
					break;
				}
				case EXP_ORB: {
					NetworkEntityDataCache ecache = entity.getDataCache();
					writeCollectSound(
						"entity.experience_orb.pickup",
						ecache.getX(), ecache.getY(), ecache.getZ(),
						0.2F, (((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 0.7F) + 1.0F) * 2.0F
					);
					break;
				}
				default: {
					break;
				}
			}
		}

		writeCollectEffect();
	}

	protected abstract void writeCollectEffect();

	protected abstract void writeCollectSound(String sound, double x, double y, double z, float volume, float pitch);

}
