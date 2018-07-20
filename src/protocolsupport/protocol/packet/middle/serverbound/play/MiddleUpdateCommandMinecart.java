package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUpdateCommandMinecart extends ServerBoundMiddlePacket {

	protected int entityId;
	protected String command;
	protected boolean trackOutput;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_UPDATE_COMMAND_MINECART);
		VarNumberSerializer.writeVarInt(creator, entityId);
		StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, command);
		creator.writeBoolean(trackOutput);
		return RecyclableSingletonList.create(creator);
	}

}
