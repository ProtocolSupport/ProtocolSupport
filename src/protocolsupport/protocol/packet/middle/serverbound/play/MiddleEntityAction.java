package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleEntityAction extends ServerBoundMiddlePacket {

	public MiddleEntityAction(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected Action action;
	protected int jumpBoost;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = MiddleEntityAction.create(entityId, action, jumpBoost);
		return RecyclableSingletonList.create(creator);
	}

	public static ServerBoundPacketData create(int entityId, Action action, int jumpBoost) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_ENTITY_ACTION);
		VarNumberSerializer.writeVarInt(creator, entityId);
		MiscSerializer.writeVarIntEnum(creator, action);
		VarNumberSerializer.writeVarInt(creator, jumpBoost);
		return creator;
	}

	protected static enum Action {
		START_SNEAK, STOP_SNEAK, LEAVE_BED, START_SPRINT, STOP_SPRINT, START_JUMP, STOP_JUMP, OPEN_HORSE_INV, START_ELYTRA_FLY;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
