package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateCommandMinecart extends ServerBoundMiddlePacket {

	public MiddleUpdateCommandMinecart(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected String command;
	protected boolean trackOutput;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_UPDATE_COMMAND_MINECART);
		VarNumberSerializer.writeVarInt(creator, entityId);
		StringSerializer.writeVarIntUTF8String(creator, command);
		creator.writeBoolean(trackOutput);
		return RecyclableSingletonList.create(creator);
	}

}
