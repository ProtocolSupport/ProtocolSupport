package protocolsupport.protocol.packet.middle.serverbound.play;

import org.bukkit.util.Vector;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleUseEntity extends ServerBoundMiddlePacket {

	public MiddleUseEntity(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected Action action;
	protected Vector interactedAt;
	protected UsedHand hand;
	protected boolean sneaking;

	@Override
	protected void writeToServer() {
		codec.read(create(entityId, action, interactedAt, hand, sneaking));
	}

	public static ServerBoundPacketData create(int entityId, Action action, Vector interactedAt, UsedHand hand, boolean sneaking) {
		ServerBoundPacketData useentity = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_USE_ENTITY);
		VarNumberSerializer.writeVarInt(useentity, entityId);
		MiscSerializer.writeVarIntEnum(useentity, action);
		switch (action) {
			case INTERACT: {
				MiscSerializer.writeVarIntEnum(useentity, hand);
				break;
			}
			case INTERACT_AT: {
				useentity.writeFloat((float) interactedAt.getX());
				useentity.writeFloat((float) interactedAt.getY());
				useentity.writeFloat((float) interactedAt.getZ());
				MiscSerializer.writeVarIntEnum(useentity, hand);
				break;
			}
			case ATTACK: {
				break;
			}
		}
		useentity.writeBoolean(sneaking);
		return useentity;
	}

	protected enum Action {
		INTERACT, ATTACK, INTERACT_AT;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
