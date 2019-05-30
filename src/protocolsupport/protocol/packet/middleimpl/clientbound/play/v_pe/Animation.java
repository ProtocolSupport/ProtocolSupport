package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3vi;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Animation extends MiddleEntityAnimation {

	private static final int ANIMATION_ARM_SWING = 1;
	private static final int ANIMATION_HURT = 2;
	private static final int ANIMATION_WAKE_UP = 3;
	private static final int ANIMATION_CRITICAL_DAMAGE = 4;
	private static final int ANIMATION_EATING_ITEM = 57;

	public Animation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		switch (animation) {
			case SWING_ARM: {
				return RecyclableSingletonList.create(create(entityId, ANIMATION_ARM_SWING));
			}
			case TAKE_DAMAGE: {
				return RecyclableSingletonList.create(EntityStatus.create(entityId, ANIMATION_HURT));
			}
			case WAKE_UP: {
				RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
				NetworkEntity player = cache.getWatchedEntityCache().getSelfPlayer();
				if (entityId == player.getId()) {
					// See UseBed for detail; basically we reverse the stuff done there
					packets.add(EntityMetadata.createFromAttribute(version, cache.getAttributesCache().getLocale(), player, EntityMetadata.PeMetaBase.BED_POSTION, new NetworkEntityMetadataObjectVector3vi(Position.ZERO)));
					// Clear player flags
					packets.add(EntityMetadata.createFromAttribute(version, cache.getAttributesCache().getLocale(), player, EntityMetadata.PeMetaBase.PLAYER_FLAGS, new NetworkEntityMetadataObjectByte((byte) 0)));
				}
				packets.add(create(entityId, ANIMATION_WAKE_UP));
				return packets;
			}
			case EAT: {
				return RecyclableSingletonList.create(EntityStatus.create(entityId, ANIMATION_EATING_ITEM));
			}
			case CRIT:
			case MAGIC_CRIT: {
				return RecyclableSingletonList.create(create(entityId, ANIMATION_CRITICAL_DAMAGE));
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
