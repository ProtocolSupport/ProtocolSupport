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

public abstract class MiddleTabComplete extends ServerBoundMiddlePacket {

	protected String string;
	protected boolean assumecommand;
	protected Position position = new Position(0, 0, 0);

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_TAB_COMPLETE);
		StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, string);
		creator.writeBoolean(assumecommand);
		if (position != null) {
			creator.writeBoolean(true);
			PositionSerializer.writePosition(creator, position);
		} else {
			creator.writeBoolean(false);
		}
		return RecyclableSingletonList.create(creator);
	}

}
