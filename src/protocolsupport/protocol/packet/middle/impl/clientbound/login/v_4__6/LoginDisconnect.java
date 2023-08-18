package protocolsupport.protocol.packet.middle.impl.clientbound.login.v_4__6;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;

public class LoginDisconnect extends MiddleLoginDisconnect implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6 {

	public LoginDisconnect(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData logindisconnect = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_DISCONNECT);
		StringCodec.writeShortUTF16BEString(logindisconnect, message.toLegacyText(cache.getClientCache().getLocale()));
		io.writeClientbound(logindisconnect);
	}

}
