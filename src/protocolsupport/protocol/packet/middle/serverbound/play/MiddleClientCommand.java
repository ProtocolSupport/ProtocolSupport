package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleClientCommand extends ServerBoundMiddlePacket {

	public MiddleClientCommand(ConnectionImpl connection) {
		super(connection);
	}

	protected Command command;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (command == null) {
			return RecyclableEmptyList.get();
		} else {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CLIENT_COMMAND);
			MiscSerializer.writeVarIntEnum(creator, command);
			return RecyclableSingletonList.create(creator);
		}
	}

	protected static enum Command {
		REQUEST_RESPAWN, GET_STATS
	}

}
