package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleTabComplete extends ServerBoundMiddlePacket {

	public MiddleTabComplete(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected String string;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_TAB_COMPLETE);
		VarNumberSerializer.writeVarInt(creator, id);
		StringSerializer.writeVarIntUTF8String(creator, string);
		return RecyclableSingletonList.create(creator);
	}

}
