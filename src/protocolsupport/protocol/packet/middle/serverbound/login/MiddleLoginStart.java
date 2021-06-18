package protocolsupport.protocol.packet.middle.serverbound.login;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleLoginStart extends ServerBoundMiddlePacket {

	protected MiddleLoginStart(MiddlePacketInit init) {
		super(init);
	}

	protected String name;

	@Override
	protected void write() {
		ServerBoundPacketData loginstart = ServerBoundPacketData.create(ServerBoundPacketType.LOGIN_START);
		StringCodec.writeVarIntUTF8String(loginstart, name);
		codec.writeServerbound(loginstart);
	}

}
