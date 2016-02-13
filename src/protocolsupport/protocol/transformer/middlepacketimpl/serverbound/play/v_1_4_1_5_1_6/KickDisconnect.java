package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class KickDisconnect extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readString(Short.MAX_VALUE);
	}

	@Override
	public RecyclableCollection<Packet<?>> toNative() throws Exception {
		return RecyclableEmptyList.get();
	}

}
