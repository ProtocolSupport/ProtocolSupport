package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public void handle() {
	}

	public boolean isValid() {
		return true;
	}

	public abstract void readFromServerData(ByteBuf serverdata);

	public abstract RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version);

}
