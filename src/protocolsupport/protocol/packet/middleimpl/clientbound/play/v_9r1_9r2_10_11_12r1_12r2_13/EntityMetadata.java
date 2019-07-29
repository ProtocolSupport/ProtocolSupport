package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import java.util.Optional;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA);
		VarNumberSerializer.writeVarInt(entitymetadata, entityId);
		NetworkEntityMetadataSerializer.writeData(entitymetadata, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());

		if (entity.getType() == NetworkEntityType.PLAYER) {
			Optional<NetworkEntityMetadataObjectOptionalPosition> bedpositionObject = NetworkEntityMetadataObjectIndex.EntityLiving.BED_LOCATION.getValue(entityRemapper.getOriginalMetadata());
			if (bedpositionObject.isPresent()) {
				Position bedposition = bedpositionObject.get().getValue();
				if (bedposition != null) {
					RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
					packets.add(entitymetadata);

					ClientBoundPacketData usebed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED_ID);
					VarNumberSerializer.writeVarInt(usebed, entityId);
					PositionSerializer.writeLegacyPositionL(usebed, bedposition);
					packets.add(usebed);

					return packets;
				}
			}
		}

		return RecyclableSingletonList.create(entitymetadata);
	}

}
