package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class ResourcePack extends MiddleResourcePack {

	public ResourcePack(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_RESOURCE_PACK);
		StringSerializer.writeVarIntUTF8String(resourcepack, url);
		StringSerializer.writeVarIntUTF8String(resourcepack, hash);
		codec.write(resourcepack);
	}

}
