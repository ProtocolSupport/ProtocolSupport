package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.CustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(ConnectionImpl connection) {
		super(connection);
	}

	protected final ByteBuf buffer = Unpooled.buffer();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		buffer.clear();
		MiscSerializer.writeVarIntEnum(buffer, hand);
		return RecyclableSingletonList.create(CustomPayload.create(version, LegacyCustomPayloadChannelName.LEGACY_BOOK_OPEN, buffer));
	}

}
