package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleOfflineServerData;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class OfflineServerData extends MiddleOfflineServerData implements
IClientboundMiddlePacketV20 {

	public OfflineServerData(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData serverdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SERVER_DATA);
		StringCodec.writeVarIntUTF8String(serverdataPacket, ChatCodec.serialize(version, clientCache.getLocale(), motd));
		OptionalCodec.writeOptionalVarIntUTF8String(serverdataPacket, icon);
		serverdataPacket.writeBoolean(secureChat);
		io.writeClientbound(serverdataPacket);
	}

}
