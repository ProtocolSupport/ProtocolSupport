package protocolsupport.protocol.v_1_7.clientboundtransformer;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.DataStorage.ProtocolVersion;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_8_R1.Packet;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public void tranform(ChannelHandlerContext ctx, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		switch (packetId) {
			case 0x01: { //PacketLoginOutEncryptionBegin
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
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
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				String uuidstring = packetdata.readString(36);
				if (serializer.getVersion() == ProtocolVersion.MINECRAFT_1_7_5) {
					uuidstring = uuidstring.replace("-", "");
				}
				serializer.writeString(uuidstring);
				serializer.writeString(packetdata.readString(16));
				return;	
			}
			default: { //Any other packet
				serializer.writeVarInt(packetId);
				packet.b(serializer);
				return;
			}
		}
	}

}
