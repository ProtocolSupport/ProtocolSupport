package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataEntityMetadata.PotionNetworkEntityData;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class AbstractPotionItemAsObjectDataEntityVelocity extends MiddleEntityVelocity {

	public AbstractPotionItemAsObjectDataEntityVelocity(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	@Override
	protected void handleReadData() {
		super.handleReadData();

		NetworkEntity entity = entityCache.getEntity(entityId);
		if (entity != null) {
			PotionNetworkEntityData potiondata = entity.getDataCache().getData(AbstractPotionItemAsObjectDataEntityMetadata.DATA_KEY);
			if (potiondata != null) {
				potiondata.updateVelocity(velX, velY, velZ);
			}
		}
	}

}
