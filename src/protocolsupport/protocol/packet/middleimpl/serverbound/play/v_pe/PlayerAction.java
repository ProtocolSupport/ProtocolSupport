package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketDecoder;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAction extends ServerBoundMiddlePacket {

	public static final int START_BREAK = 0;
	public static final int ABORT_BREAK = 1;
	public static final int STOP_BREAK = 2;
	public static final int RELEASE_ITEM = 4;
	public static final int STOP_SLEEPING = 6;
	public static final int RESPAWN1 = 7;
	public static final int START_SPRINT = 9;
	public static final int STOP_SPRINT = 10;
	public static final int START_SNEAK = 11;
	public static final int STOP_SNEAK = 12;
	public static final int DIMENSION_CHANGE = 13;
	public static final int DIMENSION_CHANGE_ACK = 14;
	public static final int START_GLIDE = 15;
	public static final int STOP_GLIDE = 16;
	public static final int BUILD_DENIED = 17;
	public static final int CONTINUE_BREAK = 18;

	public static final int SET_ENCHANTMENT_SEED = 20;
	public static final int START_SWIMMING = 21;
	public static final int STOP_SWIMMING = 22;
	public static final int START_SPIN_ATTACK = 23;
	public static final int STOP_SPIN_ATTACK = 24;

	public static boolean isDimSwitchAck(ByteBuf data) {
		if (PEPacketDecoder.sPeekPacketId(data) == PEPacketIDs.PLAYER_ACTION) {
			final ByteBuf copy = data.duplicate();
			PEPacketDecoder.sReadPacketId(copy);
			VarNumberSerializer.readVarLong(copy); // entity id
			return VarNumberSerializer.readSVarInt(copy) == DIMENSION_CHANGE_ACK;
		}
		return false;
	}

	public PlayerAction(ConnectionImpl connection) {
		super(connection);
	}

	protected int action;
	protected int face;
	protected Position blockPosition = new Position(0, 0, 0);
	protected Position breakPosition = null;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		VarNumberSerializer.readVarLong(clientdata); // entity id
		action = VarNumberSerializer.readSVarInt(clientdata);
		PositionSerializer.readPEPositionTo(clientdata, blockPosition);
		face = VarNumberSerializer.readSVarInt(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		int selfId = cache.getWatchedEntityCache().getSelfPlayerEntityId();
		switch (action) {
			case RESPAWN1:
			case DIMENSION_CHANGE: {
				ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CLIENT_COMMAND);
				VarNumberSerializer.writeSVarInt(serializer, MiddleClientCommand.Command.REQUEST_RESPAWN.ordinal());
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
				return RecyclableSingletonList.create(MiddleEntityAction.create(selfId, MiddleEntityAction.Action.START_SPRINT, 0));
			}
			case STOP_SPRINT: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(selfId, MiddleEntityAction.Action.STOP_SPRINT, 0));
			}
			case START_SNEAK: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(selfId, MiddleEntityAction.Action.START_SNEAK, 0));
			}
			case STOP_SNEAK: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(selfId, MiddleEntityAction.Action.STOP_SNEAK, 0));
			}
			case STOP_SLEEPING: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(selfId, MiddleEntityAction.Action.LEAVE_BED, 0));
			}
			case START_GLIDE: {
				return RecyclableSingletonList.create(MiddleEntityAction.create(selfId, MiddleEntityAction.Action.START_ELYTRA_FLY, 0));
			}
			case DIMENSION_CHANGE_ACK:
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
