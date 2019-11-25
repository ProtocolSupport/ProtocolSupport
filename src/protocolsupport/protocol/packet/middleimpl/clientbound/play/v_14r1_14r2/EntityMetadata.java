package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityMetadata extends MiddleEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitymetadata = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA);
		VarNumberSerializer.writeVarInt(entitymetadata, entityId);
		NetworkEntityMetadataSerializer.writeData(entitymetadata, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());
		codec.write(entitymetadata);
	}

}
