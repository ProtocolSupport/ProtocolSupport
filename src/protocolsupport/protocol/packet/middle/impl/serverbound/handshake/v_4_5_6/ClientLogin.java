package protocolsupport.protocol.packet.middle.impl.serverbound.handshake.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ClientLogin extends ServerBoundMiddlePacket implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6 {

	public ClientLogin(IMiddlePacketInit init) {
		super(init);
	}

	protected String username;
	protected String hostname;
	protected int port;

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readUnsignedByte();
		username = StringCodec.readShortUTF16BEString(clientdata, 16);
		hostname = StringCodec.readShortUTF16BEString(clientdata, Short.MAX_VALUE);
		port = clientdata.readInt();
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
