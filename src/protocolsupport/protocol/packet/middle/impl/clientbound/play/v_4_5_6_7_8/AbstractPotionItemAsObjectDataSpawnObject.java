package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataEntityMetadata.PotionNetworkEntityData;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractThunderboltSpawnObject;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractPotionItemAsObjectDataSpawnObject extends AbstractThunderboltSpawnObject {

	protected AbstractPotionItemAsObjectDataSpawnObject(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		super.handle();

		if (lType == NetworkEntityType.POTION) {
			PotionNetworkEntityData potiondata = new PotionNetworkEntityData();
			potiondata.updateVelocity(velX, velY, velZ);
			entity.getDataCache().setData(AbstractPotionItemAsObjectDataEntityMetadata.DATA_KEY, potiondata);
		}
	}

}
