package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class Ping extends ServerBoundMiddlePacket {

	public Ping(MiddlePacketInit init) {
		super(init);
	}

	protected String hostname;
	protected int port;

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readUnsignedByte();
		clientdata.readUnsignedByte();
		StringCodec.readShortUTF16BEString(clientdata, Short.MAX_VALUE);
		clientdata.readUnsignedShort();
		clientdata.readUnsignedByte();
		hostname = StringCodec.readShortUTF16BEString(clientdata, Short.MAX_VALUE);
		port = clientdata.readInt();
	}

	@Override
	protected void write() {
		ServerBoundPacketData setprotocol = ServerBoundPacketData.create(ServerBoundPacketType.HANDSHAKE_START);
		VarNumberCodec.writeVarInt(setprotocol, ProtocolVersionsHelper.LATEST_PC.getId());
		StringCodec.writeVarIntUTF8String(setprotocol, hostname);
		setprotocol.writeShort(port);
		VarNumberCodec.writeVarInt(setprotocol, 1);
		codec.writeServerbound(setprotocol);

		codec.writeServerbound(ServerBoundPacketData.create(ServerBoundPacketType.STATUS_REQUEST));
	}

}
