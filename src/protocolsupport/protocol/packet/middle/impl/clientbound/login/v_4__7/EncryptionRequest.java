package protocolsupport.protocol.packet.middle.impl.clientbound.login.v_4__7;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.login.MiddleEncryptionRequest;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;

public class EncryptionRequest extends MiddleEncryptionRequest implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7 {

	public EncryptionRequest(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData encryptionrequest = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_ENCRYPTION_BEGIN);
		StringCodec.writeString(encryptionrequest, version, serverId);
		ArrayCodec.writeShortByteArray(encryptionrequest, publicKey);
		ArrayCodec.writeShortByteArray(encryptionrequest, verifyToken);
		io.writeClientbound(encryptionrequest);
	}

}
