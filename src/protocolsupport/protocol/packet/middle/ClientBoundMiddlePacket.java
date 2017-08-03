package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public void handle() {
	}

	public abstract void readFromServerData(ByteBuf serverdata);

	public abstract RecyclableCollection<ClientBoundPacketData> toData();

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
