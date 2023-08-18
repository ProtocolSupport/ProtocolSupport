package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;

public class KickDisconnect extends ServerBoundMiddlePacket implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6 {

	public KickDisconnect(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		StringCodec.readShortUTF16BEString(clientdata, 32);
	}

	@Override
	protected void write() {
	}

}
