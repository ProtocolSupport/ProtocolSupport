package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import io.netty.buffer.ByteBufUtil;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class ResourcePack extends MiddleResourcePack {

	public ResourcePack(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeVarIntUTF8String(resourcepack, "MC|RPack");
		MiscSerializer.writeShortLengthPrefixedType(resourcepack, url, ByteBufUtil::writeUtf8);
		codec.writeClientbound(resourcepack);
	}

}
