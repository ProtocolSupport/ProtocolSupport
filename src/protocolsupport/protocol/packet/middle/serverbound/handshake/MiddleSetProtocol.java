package protocolsupport.protocol.packet.middle.serverbound.handshake;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
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
		ServerBoundPacketData setprotocol = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(setprotocol, ProtocolVersionsHelper.LATEST_PC.getId());
		StringSerializer.writeVarIntUTF8String(setprotocol, hostname);
		setprotocol.writeShort(port);
		VarNumberSerializer.writeVarInt(setprotocol, nextState);
		codec.writeServerbound(setprotocol);
	}

}
