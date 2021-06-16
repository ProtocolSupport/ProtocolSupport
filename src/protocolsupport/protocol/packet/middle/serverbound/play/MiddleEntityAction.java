package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleEntityAction extends ServerBoundMiddlePacket {

	protected MiddleEntityAction(MiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected Action action;
	protected int jumpBoost;

	@Override
	protected void write() {
		codec.writeServerbound(MiddleEntityAction.create(entityId, action, jumpBoost));
	}

	public static ServerBoundPacketData create(int entityId, Action action, int jumpBoost) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_PLAY_ENTITY_ACTION);
		VarNumberSerializer.writeVarInt(creator, entityId);
		MiscSerializer.writeVarIntEnum(creator, action);
		VarNumberSerializer.writeVarInt(creator, jumpBoost);
		return creator;
	}

	protected enum Action {
		START_SNEAK, STOP_SNEAK, LEAVE_BED, START_SPRINT, STOP_SPRINT, START_JUMP, STOP_JUMP, OPEN_HORSE_INV, START_ELYTRA_FLY;
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
