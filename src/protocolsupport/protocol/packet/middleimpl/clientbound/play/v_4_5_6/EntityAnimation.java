package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import java.util.EnumMap;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

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
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_ANIMATION);
		serializer.writeInt(entityId);
		serializer.writeByte(animationIds.get(animation));
		return RecyclableSingletonList.create(serializer);
	}

}
