package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

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
		ClientBoundPacketData collecteffect = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_COLLECT_EFFECT);
		collecteffect.writeInt(entityId);
		collecteffect.writeInt(collectorId);
		codec.write(collecteffect);
	}

}
