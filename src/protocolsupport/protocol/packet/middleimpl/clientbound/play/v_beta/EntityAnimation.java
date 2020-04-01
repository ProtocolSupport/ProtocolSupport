package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

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
	}

	public EntityAnimation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_ANIMATION);
		serializer.writeInt(entityId);
		serializer.writeByte(animationIds.getOrDefault(animation, 0));
		codec.write(serializer);
	}

}
