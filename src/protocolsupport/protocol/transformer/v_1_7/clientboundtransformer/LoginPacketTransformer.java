package protocolsupport.protocol.transformer.v_1_7.clientboundtransformer;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.Allocator;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public void tranform(ChannelHandlerContext ctx, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), serializer.getVersion());
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
				break;
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
				break;
			}
			default: { //Any other packet
				serializer.writeVarInt(packetId);
				packet.b(serializer);
				break;
			}
		}
		packetdata.release();
	}

}
