package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1__10;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCollectEffect;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;

public class CollectEffect extends MiddleCollectEffect implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10 {

	public CollectEffect(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData collecteffect = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COLLECT_EFFECT);
		VarNumberCodec.writeVarInt(collecteffect, entityId);
		VarNumberCodec.writeVarInt(collecteffect, collectorId);
		io.writeClientbound(collecteffect);
	}

}
