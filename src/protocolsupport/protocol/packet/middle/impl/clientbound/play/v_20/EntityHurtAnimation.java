package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityHurtAnimation;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class EntityHurtAnimation extends MiddleEntityHurtAnimation implements
IClientboundMiddlePacketV20 {

	public EntityHurtAnimation(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData hurtanimationData = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_HURT_ANIMATION);
		VarNumberCodec.writeVarInt(hurtanimationData, entity.getId());
		hurtanimationData.writeFloat(yaw);
		io.writeClientbound(hurtanimationData);
	}

}
