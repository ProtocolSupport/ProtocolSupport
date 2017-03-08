package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleBlockDig extends ServerBoundMiddlePacket {

	protected int status;
	protected Position position;
	protected int face;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(status, position, face));
	}

	public static ServerBoundPacketData create(int status, Position position, int face) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_BLOCK_DIG);
		VarNumberSerializer.writeVarInt(creator, status);
		PositionSerializer.writePosition(creator, position);
		creator.writeByte(face);
		return creator;
	}

}
