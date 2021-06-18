package protocolsupport.protocol.packet.middle.serverbound.play;

import org.bukkit.util.Vector;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleUseEntity extends ServerBoundMiddlePacket {

	protected MiddleUseEntity(MiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected Action action;
	protected Vector interactedAt;
	protected UsedHand hand;
	protected boolean sneaking;

	@Override
	protected void write() {
		codec.writeServerbound(create(entityId, action, interactedAt, hand, sneaking));
	}

	public static ServerBoundPacketData create(int entityId, Action action, Vector interactedAt, UsedHand hand, boolean sneaking) {
		ServerBoundPacketData useentity = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_USE_ENTITY);
		VarNumberCodec.writeVarInt(useentity, entityId);
		MiscDataCodec.writeVarIntEnum(useentity, action);
		switch (action) {
			case INTERACT: {
				MiscDataCodec.writeVarIntEnum(useentity, hand);
				break;
			}
			case INTERACT_AT: {
				useentity.writeFloat((float) interactedAt.getX());
				useentity.writeFloat((float) interactedAt.getY());
				useentity.writeFloat((float) interactedAt.getZ());
				MiscDataCodec.writeVarIntEnum(useentity, hand);
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
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
