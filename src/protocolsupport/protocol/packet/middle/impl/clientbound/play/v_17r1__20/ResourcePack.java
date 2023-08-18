package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__20;

import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class ResourcePack extends MiddleResourcePack implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18,
IClientboundMiddlePacketV20 {

	public ResourcePack(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData resourcepackPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESOURCE_PACK);
		StringCodec.writeVarIntUTF8String(resourcepackPacket, url);
		StringCodec.writeVarIntUTF8String(resourcepackPacket, hash);
		resourcepackPacket.writeBoolean(forced);
		OptionalCodec.writeOptionalVarIntUTF8String(resourcepackPacket, ChatCodec.serialize(version, clientCache.getLocale(), message));
		io.writeClientbound(resourcepackPacket);
	}

}
