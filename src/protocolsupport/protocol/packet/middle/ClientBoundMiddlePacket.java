package protocolsupport.protocol.packet.middle;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class ClientBoundMiddlePacket extends MiddlePacket {

	public void handle() {
	}

	public abstract void readFromServerData(ProtocolSupportPacketDataSerializer serializer);

	public abstract RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version);

}
