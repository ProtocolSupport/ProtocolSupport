package protocolsupport.protocol.transformer.v_1_6.clientboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.utils.Allocator;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public void tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), serializer.getVersion());
		packet.b(packetdata);
		switch (packetId) {
			case 0x00: { // PacketLoginOutDisconnect
				serializer.writeByte(0xFF);
				serializer.writeString(LegacyUtils.fromComponent(packetdata.d()));
				break;
			}
			case 0x01: { //PacketLoginOutEncryptionBegin
				serializer.writeByte(0xFD);
				packet.b(serializer);
				break;
			}
			case 0x02: { //PacketLoginOutSuccess
				//skip sending this packet, allows us to skip the need in encryption, decrypting received data is still needed
				break;
			}
		}
		packetdata.release();
	}

}
