package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAction extends ServerBoundMiddlePacket {

	protected int action;
	protected Position blockPosition;
	protected int face;

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		VarNumberSerializer.readVarLong(clientdata); // entity id
		action = VarNumberSerializer.readSVarInt(clientdata);
		blockPosition = PositionSerializer.readPEPosition(clientdata);
		face = VarNumberSerializer.readSVarInt(clientdata);
	}

	protected Position breakPosition = null;

	private static final int START_BREAK = 0;
	private static final int ABORT_BREAK = 1;
	private static final int STOP_BREAK = 2;
	private static final int RELEASE_ITEM = 5;
	private static final int STOP_SLEEPING = 6;
	private static final int RESPAWN1 = 7;
	private static final int RESPAWN2 = 13;
	private static final int START_SPRINT = 9;
	private static final int STOP_SPRINT = 10;
	private static final int START_SNEAK = 11;
	private static final int STOP_SNEAK = 12;
	private static final int START_GLIDE = 15;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		switch (action) {
			case RESPAWN1:
			case RESPAWN2: {
				ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CLIENT_COMMAND);
				VarNumberSerializer.writeSVarInt(serializer, 0);
				return RecyclableSingletonList.create(serializer);
			}
			case START_BREAK: {
				breakPosition = blockPosition.clone();
				return RecyclableSingletonList.create(MiddleBlockDig.create(MiddleBlockDig.Action.START_DIG, breakPosition, face));
			}
			case ABORT_BREAK: {
				if (breakPosition != null) {
					Position rBreakPosition = breakPosition;
					breakPosition = null;
					return RecyclableSingletonList.create(MiddleBlockDig.create(MiddleBlockDig.Action.CANCEL_DIG, rBreakPosition, face));
				} else {
					return RecyclableEmptyList.get();
				}
			}
			case STOP_BREAK: {
				if (breakPosition != null) {
					return RecyclableSingletonList.create(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_DIG, breakPosition, face));
				} else {
					return RecyclableEmptyList.get();
				}
			}
			case RELEASE_ITEM: {
				return RecyclableSingletonList.create(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_USE, new Position(0, 0, 0), 255));
			}
			case START_SPRINT: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), MiddleEntityAction.Action.START_SPRINT, 0));
			}
			case STOP_SPRINT: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), MiddleEntityAction.Action.STOP_SPRINT, 0));
			}
			case START_SNEAK: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), MiddleEntityAction.Action.START_SNEAK, 0));
			}
			case STOP_SNEAK: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), MiddleEntityAction.Action.STOP_SNEAK, 0));
			}
			case STOP_SLEEPING: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), MiddleEntityAction.Action.LEAVE_BED, 0));
			}
			case START_GLIDE: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(cache.getSelfPlayerEntityId(), MiddleEntityAction.Action.START_ELYTRA_FLY, 0));
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
