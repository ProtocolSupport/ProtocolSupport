package protocolsupport.protocol.packet.middle.base.clientbound.login;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ProfileCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleLoginSuccess extends ClientBoundMiddlePacket {

	protected MiddleLoginSuccess(IMiddlePacketInit init) {
		super(init);
	}

	protected UUID uuid;
	protected String name;
	protected ProfileProperty[] properties;

	@Override
	protected void decode(ByteBuf serverdata) {
		uuid = UUIDCodec.readUUID(serverdata);
		name = StringCodec.readVarIntUTF8String(serverdata);
		properties = ArrayCodec.readVarIntTArray(serverdata, ProfileProperty.class, ProfileCodec::readProfileProperty);
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
