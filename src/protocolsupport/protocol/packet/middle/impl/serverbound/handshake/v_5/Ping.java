package protocolsupport.protocol.packet.middle.impl.serverbound.handshake.v_5;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class Ping extends ServerBoundMiddlePacket implements IServerboundMiddlePacketV5 {

	public Ping(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readUnsignedByte();
	}

	@Override
	protected void write() {
		ServerBoundPacketData setprotocol = ServerBoundPacketData.create(ServerBoundPacketType.HANDSHAKE_START);
		VarNumberCodec.writeVarInt(setprotocol, ProtocolVersionsHelper.LATEST_PC.getId());
		StringCodec.writeVarIntUTF8String(setprotocol, "");
		setprotocol.writeShort(Bukkit.getPort());
		VarNumberCodec.writeVarInt(setprotocol, 1);
		io.writeServerbound(setprotocol);

		io.writeServerbound(ServerBoundPacketData.create(ServerBoundPacketType.STATUS_REQUEST));
	}

}
