package protocolsupport.protocol.serverboundtransformer;

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
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		switch (packetId) {
			case 0x01: { //packetLoginInEncryptionBegin
				int length1 = serializer.readUnsignedShort();
				packetdata.writeVarInt(length1);
				packetdata.writeBytes(serializer.readBytes(length1));
				int length2 = serializer.readUnsignedShort();
				packetdata.writeVarInt(length2);
				packetdata.writeBytes(serializer.readBytes(length2));
				break;
			}
		}
		if (packetdata.readableBytes() > 0) {
			packet.a(packetdata);
			return true;
		}
		return false;
	}

}
