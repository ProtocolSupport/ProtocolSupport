package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16.AbstractRemappedEntityMetadata;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class EntityMetadata extends AbstractRemappedEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA);
		VarNumberSerializer.writeVarInt(entitymetadata, entityId);
		NetworkEntityMetadataSerializer.writeData(entitymetadata, version, clientCache.getLocale(), rMetadata);
		codec.write(entitymetadata);
	}

}
