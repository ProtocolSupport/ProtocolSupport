package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCollectEffect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class CollectEffect extends MiddleCollectEffect {

	public CollectEffect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_COLLECT_EFFECT);
		serializer.writeInt(entityId);
		serializer.writeInt(collectorId);
		codec.write(serializer);
	}

}
