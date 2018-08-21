package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddlePlayerAbilities extends ClientBoundMiddlePacket {

	protected static final int flagOffsetIsFlying = 0x2;
	protected static final int flagOffsetCanFly = 0x4;

	public MiddlePlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	protected int flags;
	protected float flyspeed;
	protected float walkspeed;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		flags = serverdata.readUnsignedByte();
		flyspeed = serverdata.readFloat();
		walkspeed = serverdata.readFloat();
	}

}
