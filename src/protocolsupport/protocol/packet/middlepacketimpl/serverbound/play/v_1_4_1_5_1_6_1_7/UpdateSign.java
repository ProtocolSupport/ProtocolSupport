package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import net.minecraft.server.v1_9_R1.BlockPosition;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.play.MiddleUpdateSign;

public class UpdateSign extends MiddleUpdateSign {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		position = new BlockPosition(serializer.readInt(), serializer.readShort(), serializer.readInt());
		for (int i = 0; i < 4; i++) {
			lines[i] = serializer.readString(15);
		}
	}

}
