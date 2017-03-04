package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleInventoryData extends ClientBoundMiddlePacket {

	protected int windowId;
	protected int type;
	protected int value;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		type = serverdata.readShort();
		value = serverdata.readShort();
	}

}
