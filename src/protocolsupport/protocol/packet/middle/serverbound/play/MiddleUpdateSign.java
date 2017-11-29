package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateSign extends ServerBoundMiddlePacket {

	protected Position position = new Position(0, 0, 0);
	protected String[] lines = new String[4];

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(position, lines));
	}

	public static ServerBoundPacketData create(Position position, String[] lines) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_UPDATE_SIGN);
		PositionSerializer.writePosition(creator, position);
		for (int i = 0; i < lines.length; i++) {
			StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, lines[i]);
		}
		return creator;
	}
}
