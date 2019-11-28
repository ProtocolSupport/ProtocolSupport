package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import io.netty.buffer.ByteBufUtil;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class ResourcePack extends MiddleResourcePack {

	public ResourcePack(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(resourcepack, "MC|RPack");
		ArraySerializer.writeShortByteArray(resourcepack, to -> ByteBufUtil.writeUtf8(to, url));
		codec.write(resourcepack);
	}

}
