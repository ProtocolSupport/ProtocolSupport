package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import java.util.EnumMap;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityAnimation extends MiddleEntityAnimation {

	protected static final EnumMap<Animation, Integer> animationIds = new EnumMap<>(Animation.class);
	static {
		animationIds.put(Animation.SWING_MAIN_HAND, 1);
		animationIds.put(Animation.WAKE_UP, 3);
		animationIds.put(Animation.SWING_OFF_HAND, 5);
		animationIds.put(Animation.CRIT, 6);
		animationIds.put(Animation.MAGIC_CRIT, 7);
	}

	public EntityAnimation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData entityanimation = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_ANIMATION);
		entityanimation.writeInt(entityId);
		entityanimation.writeByte(animationIds.get(animation));
		codec.write(entityanimation);
	}

}
