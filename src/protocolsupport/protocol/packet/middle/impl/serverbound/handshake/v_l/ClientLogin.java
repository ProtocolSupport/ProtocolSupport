package protocolsupport.protocol.packet.middle.impl.serverbound.handshake.v_l;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.IServerboundMiddlePacket;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ClientLogin extends ServerBoundMiddlePacket implements IServerboundMiddlePacket {

	public ClientLogin(IMiddlePacketInit init) {
		super(init);
	}

	protected String username;
	protected String hostname;
	protected int port;

	@Override
	protected void read(ByteBuf clientdata) {
		String[] data = StringCodec.readShortUTF16BEString(clientdata, Short.MAX_VALUE).split("[;]");
		String[] addrdata = data[1].split("[:]");
		username = data[0];
		hostname = addrdata[0];
		port = Integer.parseInt(addrdata[1]);
	}

	@Override
	protected void write() {
		ServerBoundPacketData setprotocol = ServerBoundPacketData.create(ServerBoundPacketType.HANDSHAKE_START);
		VarNumberCodec.writeVarInt(setprotocol, ProtocolVersionsHelper.LATEST_PC.getId());
		StringCodec.writeVarIntUTF8String(setprotocol, hostname);
		setprotocol.writeShort(port);
		VarNumberCodec.writeVarInt(setprotocol, 2);
		io.writeServerbound(setprotocol);

		ServerBoundPacketData loginstart = ServerBoundPacketData.create(ServerBoundPacketType.LOGIN_START);
		StringCodec.writeVarIntUTF8String(loginstart, username);
		io.writeServerbound(loginstart);
	}

}
