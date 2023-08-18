package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1__16r2;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__20.AbstractDeclareTags;

public class DeclareTags extends AbstractDeclareTags implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2 {

	public DeclareTags(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData declaretagsPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_DECLARE_TAGS);
		writeBlocksTags(declaretagsPacket, tagsMap.get(key_block));
		writeItemsTags(declaretagsPacket, tagsMap.get(key_item));
		writeTags(declaretagsPacket, tagsMap.get(key_fluid));
		writeEntityTags(declaretagsPacket, tagsMap.get(key_entity_type));
		io.writeClientbound(declaretagsPacket);
	}

}
