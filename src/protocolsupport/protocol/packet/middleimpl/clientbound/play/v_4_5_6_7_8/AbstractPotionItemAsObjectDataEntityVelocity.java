package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataEntityMetadata.PotionNetworkEntityData;

public abstract class AbstractPotionItemAsObjectDataEntityVelocity extends MiddleEntityVelocity {

	protected AbstractPotionItemAsObjectDataEntityVelocity(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		PotionNetworkEntityData potiondata = entity.getDataCache().getData(AbstractPotionItemAsObjectDataEntityMetadata.DATA_KEY);
		if (potiondata != null) {
			potiondata.updateVelocity(velX, velY, velZ);
		}
	}

}
