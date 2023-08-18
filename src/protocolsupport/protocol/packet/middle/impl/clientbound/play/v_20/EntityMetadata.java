package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__20.AbstractRemappedEntityMetadata;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class EntityMetadata extends AbstractRemappedEntityMetadata implements
IClientboundMiddlePacketV20 {

	public EntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData entitymetadataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_METADATA);
		VarNumberCodec.writeVarInt(entitymetadataPacket, entity.getId());
		NetworkEntityMetadataCodec.writeData(entitymetadataPacket, version, clientCache.getLocale(), fMetadata);
		io.writeClientbound(entitymetadataPacket);
	}

}
