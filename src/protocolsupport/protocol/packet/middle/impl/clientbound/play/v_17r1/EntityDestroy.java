package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public class EntityDestroy extends MiddleEntityDestroy implements IClientboundMiddlePacketV17r1 {

	public EntityDestroy(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		for (NetworkEntity entity : entities) {
			ClientBoundPacketData entitydestroyPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_DESTROY);
			VarNumberCodec.writeVarInt(entitydestroyPacket, entity.getId());
			io.writeClientbound(entitydestroyPacket);
		}
	}

}
