package com.github.shevchik.protocolsupport.protocol.transformer.v_1_6.clientboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import com.github.shevchik.protocolsupport.protocol.PacketDataSerializer;
import com.github.shevchik.protocolsupport.protocol.transformer.utils.LegacyUtils;
import com.github.shevchik.protocolsupport.utils.Allocator;

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
				serializer.writeString(packetdata.readString(20));
				int length1 = packetdata.readVarInt();
				serializer.writeShort(length1);
				serializer.writeBytes(packetdata.readBytes(length1));
				int length2 = packetdata.readVarInt();
				serializer.writeShort(length2);
				serializer.writeBytes(packetdata.readBytes(length2));
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
