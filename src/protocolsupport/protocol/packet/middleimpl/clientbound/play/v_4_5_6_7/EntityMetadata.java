package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractEntityMetadata;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;

public class EntityMetadata extends AbstractEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected ClientBoundPacketData createEntityMetadata() {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA);
		entitymetadata.writeInt(entityId);
		NetworkEntityMetadataSerializer.writeLegacyData(entitymetadata, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());
		return entitymetadata;
	}

	@Override
	protected ClientBoundPacketData createUseBed(Position position) {
		ClientBoundPacketData usebed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED_ID);
		usebed.writeInt(entityId);
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_7_5)) {
			usebed.writeByte(0);
		}
		PositionSerializer.writeLegacyPositionB(usebed, position);
		return usebed;
	}

}
