package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleNameItem extends ServerBoundMiddlePacket {

	public MiddleNameItem(ConnectionImpl connection) {
		super(connection);
	}

	protected String name;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(name));
	}

	public static ServerBoundPacketData create(String name) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_NAME_ITEM);
		StringSerializer.writeVarIntUTF8String(serializer, name);
		return serializer;
	}

}
