package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddlePlayerAbilities extends ClientBoundMiddlePacket {

	protected MiddlePlayerAbilities(IMiddlePacketInit init) {
		super(init);
	}

	protected int flags;
	protected float flyspeed;
	protected float walkspeed;

	@Override
	protected void decode(ByteBuf serverdata) {
		flags = serverdata.readUnsignedByte();
		flyspeed = serverdata.readFloat();
		walkspeed = serverdata.readFloat();
	}

}
