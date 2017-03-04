package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateSign extends ServerBoundMiddlePacket {

	protected Position position;
	protected String[] lines = new String[4];

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_UPDATE_SIGN);
		PositionSerializer.writePosition(creator, position);
		for (int i = 0; i < lines.length; i++) {
			StringSerializer.writeString(creator, ProtocolVersion.getLatest(), lines[i]);
		}
		return RecyclableSingletonList.create(creator);
	}

}
