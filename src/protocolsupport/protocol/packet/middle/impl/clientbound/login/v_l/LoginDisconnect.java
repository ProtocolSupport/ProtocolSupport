package protocolsupport.protocol.packet.middle.impl.clientbound.login.v_l;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.login.MiddleLoginDisconnect;

public class LoginDisconnect extends MiddleLoginDisconnect {

	public LoginDisconnect(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_DISCONNECT);
		StringCodec.writeShortUTF16BEString(serializer, message.toLegacyText(cache.getClientCache().getLocale()));
		io.writeClientbound(serializer);
	}

}
