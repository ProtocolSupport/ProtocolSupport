package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16.AbstractDeclareTags;

public class DeclareTags extends AbstractDeclareTags {

	public DeclareTags(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData declaretags = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_DECLARE_TAGS);
		writeBlocksTags(declaretags, blocks);
		writeItemsTags(declaretags, items);
		writeTags(declaretags, fluids);
		writeTags(declaretags, entities);
		codec.write(declaretags);
	}

}
