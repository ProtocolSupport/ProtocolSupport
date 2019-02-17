package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleInventoryData extends ClientBoundMiddlePacket {

	public MiddleInventoryData(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected int type;
	protected int value;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		type = serverdata.readShort();
		value = serverdata.readShort();
	}

	@Override
	public boolean postFromServerRead() {
		return cache.getWindowCache().isValidWindowId(windowId);
	}

}
