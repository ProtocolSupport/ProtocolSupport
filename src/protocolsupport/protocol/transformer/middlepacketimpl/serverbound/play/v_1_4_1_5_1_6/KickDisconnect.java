package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;

public class KickDisconnect extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		serializer.readString(Short.MAX_VALUE);
	}

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		return Collections.emptyList();
	}

}
