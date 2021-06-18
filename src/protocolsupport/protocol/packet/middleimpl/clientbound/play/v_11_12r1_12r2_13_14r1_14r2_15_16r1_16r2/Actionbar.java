package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleActionbar;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Actionbar extends MiddleActionbar {

	public Actionbar(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData actionbarPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ACTIONBAR);
		VarNumberCodec.writeVarInt(actionbarPacket, 2); //legacy title action (2 - set actionbar text)
		StringCodec.writeVarIntUTF8String(actionbarPacket, ChatCodec.serialize(version, clientCache.getLocale(), text));
		codec.writeClientbound(actionbarPacket);
	}

}
