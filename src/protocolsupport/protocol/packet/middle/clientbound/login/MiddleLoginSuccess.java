package protocolsupport.protocol.packet.middle.clientbound.login;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleLoginSuccess extends ClientBoundMiddlePacket {

	protected MiddleLoginSuccess(MiddlePacketInit init) {
		super(init);
	}

	protected UUID uuid;
	protected String name;

	@Override
	protected void decode(ByteBuf serverdata) {
		uuid = UUIDCodec.readUUID4I(serverdata);
		name = StringCodec.readVarIntUTF8String(serverdata);
	}

	@Override
	protected void handle() {
		cache.getClientCache().setUUID(uuid);
	}

	@Override
	protected void cleanup() {
		uuid = null;
		name = null;
	}

}
