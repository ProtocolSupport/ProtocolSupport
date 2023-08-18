package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__20.AbstractDeclareTags;

public class DeclareTags extends AbstractDeclareTags implements IClientboundMiddlePacketV13 {

	public DeclareTags(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData declaretagsPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_DECLARE_TAGS);
		writeBlocksTags(declaretagsPacket, tagsMap.get(key_block));
		writeItemsTags(declaretagsPacket, tagsMap.get(key_item));
		writeTags(declaretagsPacket, tagsMap.get(key_fluid));
		io.writeClientbound(declaretagsPacket);
	}

}
