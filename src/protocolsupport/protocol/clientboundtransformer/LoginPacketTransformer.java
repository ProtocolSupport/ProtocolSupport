package protocolsupport.protocol.clientboundtransformer;

import java.io.IOException;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.Packet;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public boolean tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		if (serializer.getVersion() == DataStorage.CLIENT_1_8_PROTOCOL_VERSION) {
			return false;
		}
		switch (packetId) {
			case 0x01: { //PacketLoginOutEncryptionBegin
				PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
				packet.b(packetdata);
				serializer.writeString(packetdata.readString(20));
				int length1 = packetdata.readVarInt();
				serializer.writeShort(length1);
				serializer.writeBytes(packetdata.readBytes(length1));
				int length2 = packetdata.readVarInt();
				serializer.writeShort(length2);
				serializer.writeBytes(packetdata.readBytes(length2));
				return true;
			}
			case 0x02: { //PacketLoginOutSuccess
				PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
				packet.b(packetdata);
				String uuidstring = packetdata.readString(36);
				if (serializer.getVersion() == 4) {
					uuidstring = uuidstring.replace("-", "");
				}
				serializer.writeString(uuidstring);
				serializer.writeString(packetdata.readString(16));
				return true;	
			}
		}
		return false;
	}

}
