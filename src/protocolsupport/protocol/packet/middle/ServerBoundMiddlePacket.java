package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	public ServerBoundMiddlePacket(ConnectionImpl connection) {
		super(connection);
	}

	public abstract void readFromClientData(ByteBuf clientdata);

	public abstract RecyclableCollection<ServerBoundPacketData> toNative();

}
