package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataEntityMetadata.PotionNetworkEntityData;

public abstract class AbstractPotionItemAsObjectDataEntityVelocity extends MiddleEntityVelocity {

	protected AbstractPotionItemAsObjectDataEntityVelocity(IMiddlePacketInit init) {
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
