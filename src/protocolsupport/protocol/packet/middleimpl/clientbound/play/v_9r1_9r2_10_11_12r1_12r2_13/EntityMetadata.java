package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractPlayerUseBedAsPacketEntityMetadata;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.Position;

public class EntityMetadata extends AbstractPlayerUseBedAsPacketEntityMetadata {

	public EntityMetadata(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_METADATA);
		VarNumberSerializer.writeVarInt(entitymetadata, entityId);
		NetworkEntityMetadataSerializer.writeData(entitymetadata, version, clientCache.getLocale(), remappedMetadata);
		codec.writeClientbound(entitymetadata);
	}

	@Override
	protected void writePlayerUseBed(Position position) {
		ClientBoundPacketData usebed = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED);
		VarNumberSerializer.writeVarInt(usebed, entityId);
		PositionSerializer.writeLegacyPositionL(usebed, position);
		codec.writeClientbound(usebed);
	}

}
