package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6;

import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class KickDisconnect extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readString();
	}

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		return RecyclableEmptyList.get();
	}

}
