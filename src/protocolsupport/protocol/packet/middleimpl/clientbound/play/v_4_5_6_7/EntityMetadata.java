package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Optional;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;

public class EntityMetadata extends MiddleEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA);
		entitymetadata.writeInt(entityId);
		NetworkEntityMetadataSerializer.writeLegacyData(entitymetadata, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());
		codec.write(entitymetadata);

		if (entity.getType() == NetworkEntityType.PLAYER) {
			Optional<NetworkEntityMetadataObjectOptionalPosition> bedpositionObject = NetworkEntityMetadataObjectIndex.EntityLiving.BED_LOCATION.getValue(entityRemapper.getOriginalMetadata());
			if (bedpositionObject.isPresent()) {
				Position bedposition = bedpositionObject.get().getValue();
				if (bedposition != null) {
					ClientBoundPacketData usebed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED_ID);
					usebed.writeInt(entityId);
					if (version.isBefore(ProtocolVersion.MINECRAFT_1_7_5)) {
						usebed.writeByte(0);
					}
					PositionSerializer.writeLegacyPositionB(usebed, bedposition);
					codec.write(usebed);
				}
			}
		}
	}

}
