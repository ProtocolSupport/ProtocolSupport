package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareTags;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class DeclareTags extends MiddleDeclareTags {

	public DeclareTags(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_DECLARE_TAGS);
		writeTags(serializer, blocks);
		writeTags(serializer, items);
		writeTags(serializer, fluids);
		writeTags(serializer, entities);
		return RecyclableSingletonList.create(serializer);
	}

	protected static void writeTags(ByteBuf to, Tag[] tags) {
		ArraySerializer.writeVarIntTArray(to, tags, (lTo, tag) -> {
			StringSerializer.writeVarIntUTF8String(lTo, tag.getTagId());
			ArraySerializer.writeVarIntVarIntArray(lTo, tag.getTaggedIds());
		});
	}

}
