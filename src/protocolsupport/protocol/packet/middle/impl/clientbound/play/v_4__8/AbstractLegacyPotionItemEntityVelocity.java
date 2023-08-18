package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractLegacyPotionItemEntityMetadata.PotionNetworkEntityData;

public abstract class AbstractLegacyPotionItemEntityVelocity extends MiddleEntityVelocity {

	protected AbstractLegacyPotionItemEntityVelocity(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		PotionNetworkEntityData potiondata = entity.getDataCache().getData(PotionNetworkEntityData.DATA_KEY);
		if (potiondata != null) {
			potiondata.updateVelocity(velX, velY, velZ);
		}
	}

}
