package protocolsupport.protocol.packet.middle.base.serverbound.login;

import java.util.UUID;

import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleLoginStart extends ServerBoundMiddlePacket {

	protected MiddleLoginStart(IMiddlePacketInit init) {
		super(init);
	}

	protected String name;
	protected UUID uuid;

	@Override
	protected void write() {
		ServerBoundPacketData loginstart = ServerBoundPacketData.create(ServerBoundPacketType.LOGIN_START);
		StringCodec.writeVarIntUTF8String(loginstart, name);
		OptionalCodec.writeOptional(loginstart, uuid, UUIDCodec::writeUUID);
		io.writeServerbound(loginstart);
	}

}
