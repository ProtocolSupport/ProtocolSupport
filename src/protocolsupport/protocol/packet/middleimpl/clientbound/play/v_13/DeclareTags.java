package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareTags;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class DeclareTags extends MiddleDeclareTags {

	public DeclareTags(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_DECLARE_TAGS);
		writeTags(serializer, blocks);
		writeTags(serializer, items);
		writeTags(serializer, fluids);
		return RecyclableSingletonList.create(serializer);
	}

	protected static void writeTags(ByteBuf to, Tag[] tags) {
		ArraySerializer.writeVarIntTArray(to, tags, (lTo, tag) -> {
			StringSerializer.writeVarIntUTF8String(lTo, tag.getTagId());
			ArraySerializer.writeVarIntVarIntArray(lTo, tag.getTaggedIds());
		});
	}

}
