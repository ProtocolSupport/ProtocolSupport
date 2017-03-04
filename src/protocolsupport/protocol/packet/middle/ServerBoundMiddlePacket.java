package protocolsupport.protocol.packet.middle;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	public abstract void readFromClientData(ByteBuf clientdata, ProtocolVersion version);

	public abstract RecyclableCollection<ServerBoundPacketData> toNative();

}
