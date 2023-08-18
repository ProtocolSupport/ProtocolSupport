package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1__18;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractLegacyPaintingEntityMetadata;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1__16r2.EntityDestroy;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.types.Position;

//TODO: fix packet (painting respawn should use correct entity destroy)
public class EntityMetadata extends AbstractLegacyPaintingEntityMetadata implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public EntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_METADATA);
		VarNumberCodec.writeVarInt(entitymetadata, entity.getId());
		NetworkEntityMetadataCodec.writeData(entitymetadata, version, clientCache.getLocale(), fMetadata);
		io.writeClientbound(entitymetadata);
	}

	@Override
	protected void writePaintingRespawn(Position position, int direction, int variant) {
		io.writeClientbound(EntityDestroy.create(entity.getId()));

		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_PAINTING);
		VarNumberCodec.writeVarInt(spawnpainting, entity.getId());
		UUIDCodec.writeUUID(spawnpainting, entity.getUUID());
		VarNumberCodec.writeVarInt(spawnpainting, variant);
		PositionCodec.writePosition(spawnpainting, position);
		spawnpainting.writeByte(direction);
		io.writeClientbound(spawnpainting);
	}

}
