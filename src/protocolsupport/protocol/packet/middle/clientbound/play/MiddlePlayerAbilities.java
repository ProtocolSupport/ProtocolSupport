package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddlePlayerAbilities extends ClientBoundMiddlePacket {

	public MiddlePlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	protected int flags;
	protected float flyspeed;
	protected float walkspeed;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		flags = serverdata.readUnsignedByte();
		flyspeed = serverdata.readFloat();
		walkspeed = serverdata.readFloat();
	}

}
