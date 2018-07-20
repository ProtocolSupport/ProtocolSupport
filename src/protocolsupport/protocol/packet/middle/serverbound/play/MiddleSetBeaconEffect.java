package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSetBeaconEffect extends ServerBoundMiddlePacket {

	protected int primary;
	protected int secondary;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_SET_BEACON_EFFECT);
		VarNumberSerializer.writeVarInt(creator, primary);
		VarNumberSerializer.writeVarInt(creator, secondary);
		return RecyclableSingletonList.create(creator);
	}

}
