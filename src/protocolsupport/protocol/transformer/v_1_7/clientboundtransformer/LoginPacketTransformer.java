package protocolsupport.protocol.transformer.v_1_7.clientboundtransformer;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientboundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.Allocator;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public void transform(ChannelHandlerContext ctx, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.getLatest());
		switch (packetId) {
			case ClientboundPacket.LOGIN_SUCCESS_ID: {
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
			default: {
				serializer.writeVarInt(packetId);
				packet.b(serializer);
				break;
			}
		}
		packetdata.release();
	}

}
