package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class ResourcePack extends MiddleResourcePack {

	public ResourcePack(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData resourcepackPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_RESOURCE_PACK);
		StringSerializer.writeVarIntUTF8String(resourcepackPacket, url);
		StringSerializer.writeVarIntUTF8String(resourcepackPacket, hash);
		resourcepackPacket.writeBoolean(forced);
		ChatSerializer.serialize(version, clientCache.getLocale(), forcedText);
		codec.writeClientbound(resourcepackPacket);
	}

}
