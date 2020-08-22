package protocolsupport.protocol.packet.middle.serverbound.handshake;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleSetProtocol extends ServerBoundMiddlePacket {

	public MiddleSetProtocol(MiddlePacketInit init) {
		super(init);
	}

	protected String hostname;
	protected int port;
	protected int nextState;

	@Override
	protected void writeToServer() {
		ServerBoundPacketData setprotocol = ServerBoundPacketData.create(PacketType.SERVERBOUND_HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(setprotocol, ProtocolVersionsHelper.LATEST_PC.getId());
		StringSerializer.writeVarIntUTF8String(setprotocol, hostname);
		setprotocol.writeShort(port);
		VarNumberSerializer.writeVarInt(setprotocol, nextState);
		codec.read(setprotocol);
	}

}
