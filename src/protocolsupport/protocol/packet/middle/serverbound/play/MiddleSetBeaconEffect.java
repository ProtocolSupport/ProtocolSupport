package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSetBeaconEffect extends ServerBoundMiddlePacket {

	public MiddleSetBeaconEffect(ConnectionImpl connection) {
		super(connection);
	}

	protected int primary;
	protected int secondary;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(primary, secondary));
	}

	public static ServerBoundPacketData create(int primary, int secondary) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_SET_BEACON_EFFECT);
		VarNumberSerializer.writeVarInt(serializer, primary);
		VarNumberSerializer.writeVarInt(serializer, secondary);
		return serializer;
	}

}
