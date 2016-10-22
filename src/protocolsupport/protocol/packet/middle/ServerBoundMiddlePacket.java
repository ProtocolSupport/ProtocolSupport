package protocolsupport.protocol.packet.middle;

import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ServerBoundMiddlePacket extends MiddlePacket {

	public abstract void readFromClientData(ProtocolSupportPacketDataSerializer serializer);

	public abstract RecyclableCollection<ServerBoundPacketData> toNative();

}
