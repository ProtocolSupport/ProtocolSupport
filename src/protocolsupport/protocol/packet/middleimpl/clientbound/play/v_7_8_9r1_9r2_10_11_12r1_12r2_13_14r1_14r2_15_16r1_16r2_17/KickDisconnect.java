package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKickDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;

public class KickDisconnect extends MiddleKickDisconnect {

	public KickDisconnect(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData kickdisconnect = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_KICK_DISCONNECT);
		StringSerializer.writeVarIntUTF8String(kickdisconnect, ChatSerializer.serialize(version, cache.getClientCache().getLocale(), message));
		codec.writeClientbound(kickdisconnect);
	}

}
