package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
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
import protocolsupport.zplatform.ServerPlatform;

public class PlayerAction extends ServerBoundMiddlePacket {

	public PlayerAction(ConnectionImpl connection) {
		super(connection);
	}

	protected int action;
	protected Position blockPosition = new Position(0, 0, 0);
	protected int face;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		VarNumberSerializer.readVarLong(clientdata); // entity id
		action = VarNumberSerializer.readSVarInt(clientdata);
		PositionSerializer.readPEPositionTo(clientdata, blockPosition);
		face = VarNumberSerializer.readSVarInt(clientdata);
	}

	protected Position breakPosition = null;

	protected static final int START_BREAK = 0;
	protected static final int ABORT_BREAK = 1;
	protected static final int STOP_BREAK = 2;
	protected static final int RELEASE_ITEM = 4;
	protected static final int STOP_SLEEPING = 6;
	protected static final int RESPAWN1 = 7;
	protected static final int START_SPRINT = 9;
	protected static final int STOP_SPRINT = 10;
	protected static final int START_SNEAK = 11;
	protected static final int STOP_SNEAK = 12;
	protected static final int DIMENSION_CHANGE = 13;
	protected static final int DIMENSION_CHANGE_ACK = 14;
	protected static final int START_GLIDE = 15;
	protected static final int STOP_GLIDE = 16;
	protected static final int BUILD_DENIED = 17;
	protected static final int CONTINUE_BREAK = 18;

	protected static final int SET_ENCHANTMENT_SEED = 20;
	protected static final int START_SWIMMING = 21;
	protected static final int STOP_SWIMMING = 22;
	protected static final int START_SPIN_ATTACK = 23;
	protected static final int STOP_SPIN_ATTACK = 24;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		int selfId = cache.getWatchedEntityCache().getSelfPlayerEntityId();
		switch (action) {
			case RESPAWN1:
			case DIMENSION_CHANGE: {
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
			case DIMENSION_CHANGE_ACK: {
				connection.getNetworkManagerWrapper().getChannel().eventLoop().schedule(() -> {
					cache.getPEPacketQueue().unlock();
					connection.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("push_q"));
				}, 10, TimeUnit.SECONDS);
				return RecyclableEmptyList.get();
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

}
