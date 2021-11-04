package protocolsupport.protocol.packet.middle.base.serverbound.handshake;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleSetProtocol extends ServerBoundMiddlePacket {

	protected MiddleSetProtocol(IMiddlePacketInit init) {
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
		io.writeServerbound(setprotocol);
	}

}
