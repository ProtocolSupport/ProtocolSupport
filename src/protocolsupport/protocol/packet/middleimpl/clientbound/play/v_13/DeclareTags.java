package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17.AbstractDeclareTags;

public class DeclareTags extends AbstractDeclareTags {

	public DeclareTags(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData declaretagsPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_DECLARE_TAGS);
		writeBlocksTags(declaretagsPacket, tagsMap.get(key_block));
		writeItemsTags(declaretagsPacket, tagsMap.get(key_item));
		writeTags(declaretagsPacket, tagsMap.get(key_fluid));
		codec.writeClientbound(declaretagsPacket);
	}

}
