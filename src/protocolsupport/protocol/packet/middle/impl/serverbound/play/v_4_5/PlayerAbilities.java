package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4_5;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;

public class PlayerAbilities extends MiddlePlayerAbilities implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5 {

	public PlayerAbilities(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		flags = clientdata.readUnsignedByte();
		clientdata.skipBytes(Byte.BYTES * 2); //fly+walk speeds
	}

}
