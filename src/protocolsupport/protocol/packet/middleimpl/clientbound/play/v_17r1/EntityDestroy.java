package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public class EntityDestroy extends MiddleEntityDestroy {

	public EntityDestroy(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		for (NetworkEntity entity : entities) {
			ClientBoundPacketData entitydestroyPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_DESTROY);
			VarNumberCodec.writeVarInt(entitydestroyPacket, entity.getId());
			codec.writeClientbound(entitydestroyPacket);
		}
	}

}
