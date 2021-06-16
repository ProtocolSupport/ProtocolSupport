package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_l;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ClientLogin extends ServerBoundMiddlePacket {

	public ClientLogin(MiddlePacketInit init) {
		super(init);
	}

	protected String username;
	protected String hostname;
	protected int port;

	@Override
	protected void read(ByteBuf clientdata) {
		String[] data = StringSerializer.readShortUTF16BEString(clientdata, Short.MAX_VALUE).split("[;]");
		String[] addrdata = data[1].split("[:]");
		username = data[0];
		hostname = addrdata[0];
		port = Integer.parseInt(addrdata[1]);
	}

	@Override
	protected void write() {
		ServerBoundPacketData setprotocol = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(setprotocol, ProtocolVersionsHelper.LATEST_PC.getId());
		StringSerializer.writeVarIntUTF8String(setprotocol, hostname);
		setprotocol.writeShort(port);
		VarNumberSerializer.writeVarInt(setprotocol, 2);
		codec.writeServerbound(setprotocol);

		ServerBoundPacketData loginstart = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_LOGIN_START);
		StringSerializer.writeVarIntUTF8String(loginstart, username);
		codec.writeServerbound(loginstart);
	}

}
