package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class ResourcePack extends MiddleResourcePack {

	public ResourcePack(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData resourcepack = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESOURCE_PACK);
		StringSerializer.writeVarIntUTF8String(resourcepack, url);
		StringSerializer.writeVarIntUTF8String(resourcepack, hash);
		codec.writeClientbound(resourcepack);
	}

}
