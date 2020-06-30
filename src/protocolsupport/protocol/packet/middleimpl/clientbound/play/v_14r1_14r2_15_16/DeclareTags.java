package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareTags;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class DeclareTags extends MiddleDeclareTags {

	public DeclareTags(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData declaretags = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_DECLARE_TAGS);
		writeTags(declaretags, blocks);
		writeTags(declaretags, items);
		writeTags(declaretags, fluids);
		writeTags(declaretags, entities);
		codec.write(declaretags);
	}

	protected static void writeTags(ByteBuf to, Tag[] tags) {
		ArraySerializer.writeVarIntTArray(to, tags, (lTo, tag) -> {
			StringSerializer.writeVarIntUTF8String(lTo, tag.getTagId());
			ArraySerializer.writeVarIntVarIntArray(lTo, tag.getTaggedIds());
		});
	}

}
