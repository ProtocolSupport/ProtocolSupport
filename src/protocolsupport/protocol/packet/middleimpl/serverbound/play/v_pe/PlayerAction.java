package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAction extends ServerBoundMiddlePacket {

	protected int action;
	protected int blockX;
	protected int blockY;
	protected int blockZ;
	protected int face;

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		VarNumberSerializer.readVarLong(clientdata); //entity id
		action = VarNumberSerializer.readSVarInt(clientdata);
		blockX = VarNumberSerializer.readSVarInt(clientdata);
		blockY = VarNumberSerializer.readVarInt(clientdata);
		blockZ = VarNumberSerializer.readSVarInt(clientdata);
		face = VarNumberSerializer.readSVarInt(clientdata);
	}

	protected Position breakPosition = null;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		switch (action) {
			case 7: {
				ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CLIENT_COMMAND);
				VarNumberSerializer.writeSVarInt(serializer, 0);
				return RecyclableSingletonList.create(serializer);
			}
			case 0: {
				breakPosition = new Position(blockX, blockY, blockZ);
				return RecyclableSingletonList.create(MiddleBlockDig.create(0, breakPosition, face));
			}
			case 1: {
				if (breakPosition != null) {
					Position rBreakPosition = breakPosition;
					breakPosition = null;
					return RecyclableSingletonList.create(MiddleBlockDig.create(1, rBreakPosition, face));
				} else {
					return RecyclableEmptyList.get();
				}
			}
			case 2: {
				if (breakPosition != null) {
					return RecyclableSingletonList.create(MiddleBlockDig.create(2, breakPosition, face));
				} else {
					return RecyclableEmptyList.get();
				}
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
