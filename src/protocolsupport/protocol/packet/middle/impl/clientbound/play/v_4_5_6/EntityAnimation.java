package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6;

import java.util.EnumMap;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityAnimation;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;

public class EntityAnimation extends MiddleEntityAnimation implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	protected static final EnumMap<Animation, Integer> animationIds = new EnumMap<>(Animation.class);
	static {
		animationIds.put(Animation.SWING_MAIN_HAND, 1);
		animationIds.put(Animation.WAKE_UP, 3);
		animationIds.put(Animation.SWING_OFF_HAND, 5);
		animationIds.put(Animation.CRIT, 6);
		animationIds.put(Animation.MAGIC_CRIT, 7);
	}

	public EntityAnimation(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityanimation = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_ANIMATION);
		entityanimation.writeInt(entityId);
		entityanimation.writeByte(animationIds.get(animation));
		io.writeClientbound(entityanimation);
	}

}
