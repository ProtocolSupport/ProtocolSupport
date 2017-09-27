package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public abstract void readFromServerData(ByteBuf serverdata);

	public boolean postFromServerRead() {
		return true;
	}

	public abstract RecyclableCollection<ClientBoundPacketData> toData();

}
