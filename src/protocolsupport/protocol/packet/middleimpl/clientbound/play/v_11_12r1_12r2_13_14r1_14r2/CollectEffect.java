package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCollectEffect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class CollectEffect extends MiddleCollectEffect {

	public CollectEffect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData collecteffect = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_COLLECT_EFFECT);
		VarNumberSerializer.writeVarInt(collecteffect, entityId);
		VarNumberSerializer.writeVarInt(collecteffect, collectorId);
		VarNumberSerializer.writeVarInt(collecteffect, itemCount);
		codec.write(collecteffect);
	}

}
