package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractPlayerUseBedAsPacketEntityMetadata;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1__16r2.EntityDestroy;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.Position;

public class EntityMetadata extends AbstractPlayerUseBedAsPacketEntityMetadata implements IClientboundMiddlePacketV13 {

	public EntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_METADATA);
		VarNumberCodec.writeVarInt(entitymetadata, entity.getId());
		NetworkEntityMetadataCodec.writeData(entitymetadata, version, clientCache.getLocale(), remappedMetadata);
		io.writeClientbound(entitymetadata);
	}

	@Override
	protected void writePlayerUseBed(Position position) {
		ClientBoundPacketData usebed = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_USE_BED);
		VarNumberCodec.writeVarInt(usebed, entity.getId());
		PositionCodec.writePositionLXYZ(usebed, position);
		io.writeClientbound(usebed);
	}

	@Override
	protected void writePaintingRespawn(Position position, int direction, int variant) {
		io.writeClientbound(EntityDestroy.create(entity.getId()));

		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_PAINTING);
		VarNumberCodec.writeVarInt(spawnpainting, entity.getId());
		UUIDCodec.writeUUID(spawnpainting, entity.getUUID());
		VarNumberCodec.writeVarInt(spawnpainting, variant);
		PositionCodec.writePositionLXYZ(spawnpainting, position);
		spawnpainting.writeByte(direction);
		io.writeClientbound(spawnpainting);
	}

}
