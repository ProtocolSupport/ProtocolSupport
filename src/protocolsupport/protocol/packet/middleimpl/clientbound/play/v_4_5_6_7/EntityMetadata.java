package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Optional;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.DataWatcherSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
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
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_METADATA_ID);
		entitymetadata.writeInt(entityId);
		DataWatcherSerializer.writeLegacyData(entitymetadata, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());

		if (entity.getType() == NetworkEntityType.PLAYER) {
			Optional<NetworkEntityMetadataObjectOptionalPosition> bedpositionObject = NetworkEntityMetadataObjectIndex.EntityLiving.BED_LOCATION.getValue(entityRemapper.getOriginalMetadata());
			if (bedpositionObject.isPresent()) {
				Position bedposition = bedpositionObject.get().getValue();
				if (bedposition != null) {
					RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
					packets.add(entitymetadata);

					ClientBoundPacketData usebed = ClientBoundPacketData.create(ClientBoundPacket.LEGACY_PLAY_USE_BED_ID);
					usebed.writeInt(entityId);
					if (version.isBefore(ProtocolVersion.MINECRAFT_1_7_5)) {
						usebed.writeByte(0);
					}
					PositionSerializer.writeLegacyPositionB(usebed, bedposition);
					packets.add(usebed);

					return packets;
				}
			}
		}

		return RecyclableSingletonList.create(entitymetadata);
	}

}
