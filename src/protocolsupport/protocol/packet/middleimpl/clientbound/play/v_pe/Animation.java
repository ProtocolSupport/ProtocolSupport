package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Animation extends MiddleEntityAnimation {

	public Animation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		switch (animation) {
			case SWING_ARM: {
				return RecyclableSingletonList.create(create(entityId, 1));
			}
			case TAKE_DAMAGE: {
				return RecyclableSingletonList.create(EntityStatus.create(entityId, 2)); //HURT_ANIMATION
			}
			case WAKE_UP: {
				return RecyclableSingletonList.create(create(entityId, 3));
			}
			case EAT: {
				return RecyclableSingletonList.create(EntityStatus.create(entityId, 57)); //EATING_ITEM
			}
			case CRIT:
			case MAGIC_CRIT: {
				return RecyclableSingletonList.create(create(entityId, 4));
			}
			default: {
				return RecyclableEmptyList.get();
			}
		}
	}

	private static ClientBoundPacketData create(int entityId, int animation) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ANIMATION);
		VarNumberSerializer.writeSVarInt(serializer, animation);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		return serializer;
	}

}
