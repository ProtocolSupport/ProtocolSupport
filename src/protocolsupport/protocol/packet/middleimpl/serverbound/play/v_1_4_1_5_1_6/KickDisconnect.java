package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4_1_5_1_6;

import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;
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
