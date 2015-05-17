package protocolsupport.protocol.transformer.v_1_5.clientboundtransformer;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.protocol.PacketDataSerializer;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public void tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		packet.b(packetdata);
		switch (packetId) {
			case 0x01: { //PacketLoginOutEncryptionBegin
				serializer.writeByte(0xFD);
				serializer.writeString(packetdata.readString(20));
				int length1 = packetdata.readVarInt();
				serializer.writeShort(length1);
				serializer.writeBytes(packetdata.readBytes(length1));
				int length2 = packetdata.readVarInt();
				serializer.writeShort(length2);
				serializer.writeBytes(packetdata.readBytes(length2));
				return;
			}
			case 0x02: { //PacketLoginOutSuccess
				//skip sending this packet, allows us to skip the need in encryption, decrypting received data is still needed
				return;
			}
		}
	}

}
