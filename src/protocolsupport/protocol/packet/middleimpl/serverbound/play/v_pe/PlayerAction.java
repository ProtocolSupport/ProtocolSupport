package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
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
		VarNumberSerializer.readVarLong(clientdata); // entity id
		action = VarNumberSerializer.readSVarInt(clientdata);
		blockX = VarNumberSerializer.readSVarInt(clientdata);
		blockY = VarNumberSerializer.readVarInt(clientdata);
		blockZ = VarNumberSerializer.readSVarInt(clientdata);
		face = VarNumberSerializer.readSVarInt(clientdata);
	}

	protected Position breakPosition = null;

	private static final int START_BREAK = 0;
	private static final int ABORT_BREAK = 1;
	private static final int STOP_BREAK = 2;
	private static final int RELEASE_ITEM = 5;
	private static final int STOP_SLEEPING = 6;
	private static final int RESPAWN = 7;
	private static final int START_SPRINT = 9;
	private static final int STOP_SPRINT = 10;
	private static final int START_SNEAK = 11;
	private static final int STOP_SNEAK = 12;
	private static final int START_GLIDE = 15;
	private static final int STOP_GLIDE = 16;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		switch (action) {
			case RESPAWN: {
				ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CLIENT_COMMAND);
				VarNumberSerializer.writeSVarInt(serializer, 0);
				return RecyclableSingletonList.create(serializer);
			}
			case START_BREAK: {
				breakPosition = new Position(blockX, blockY, blockZ);
				return RecyclableSingletonList.create(MiddleBlockDig.create(0, breakPosition, face));
			}
			case ABORT_BREAK: {
				if (breakPosition != null) {
					Position rBreakPosition = breakPosition;
					breakPosition = null;
					return RecyclableSingletonList.create(MiddleBlockDig.create(1, rBreakPosition, face));
				} else {
					return RecyclableEmptyList.get();
				}
			}
			case STOP_BREAK: {
				if (breakPosition != null) {
					return RecyclableSingletonList.create(MiddleBlockDig.create(2, breakPosition, face));
				} else {
					return RecyclableEmptyList.get();
				}
			}
			case START_SPRINT: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), 3, 0));
			}
			case STOP_SPRINT: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), 4, 0));
			}
			case START_SNEAK: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), 0, 0));
			}
			case STOP_SNEAK: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), 1, 0));
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
