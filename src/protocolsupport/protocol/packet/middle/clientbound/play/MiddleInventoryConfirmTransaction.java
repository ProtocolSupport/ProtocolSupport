package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleInventoryConfirmTransaction extends ClientBoundMiddlePacket {

	protected int windowId;
	protected int actionNumber;
	protected boolean accepted;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		actionNumber = serverdata.readShort();
		accepted = serverdata.readBoolean();
	}

}
