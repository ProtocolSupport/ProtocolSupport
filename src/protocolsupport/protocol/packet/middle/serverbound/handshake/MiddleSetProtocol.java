package protocolsupport.protocol.packet.middle.serverbound.handshake;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleSetProtocol extends ServerBoundMiddlePacket {

	protected MiddleSetProtocol(MiddlePacketInit init) {
		super(init);
	}

	protected String hostname;
	protected int port;
	protected int nextState;

	@Override
	protected void write() {
		ServerBoundPacketData setprotocol = ServerBoundPacketData.create(ServerBoundPacketType.HANDSHAKE_START);
		VarNumberCodec.writeVarInt(setprotocol, ProtocolVersionsHelper.LATEST_PC.getId());
		StringCodec.writeVarIntUTF8String(setprotocol, hostname);
		setprotocol.writeShort(port);
		VarNumberCodec.writeVarInt(setprotocol, nextState);
		codec.writeServerbound(setprotocol);
	}

}
