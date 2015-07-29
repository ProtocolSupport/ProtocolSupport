package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class HandshakePacketTransformer implements PacketTransformer {

	@Override
	public void tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer, PacketDataSerializer packetdata) throws IOException {
		switch (packetId) {
			case 0x00: {
				serializer.readVarInt();
				packetdata.writeVarInt(ProtocolVersion.MINECRAFT_1_8.getId());
				packetdata.writeString(serializer.readString(32767));
				packetdata.writeShort(serializer.readUnsignedShort());
				packetdata.writeVarInt(serializer.readVarInt());
				packet.a(packetdata);
				break;
			}
			default: {
				packet.a(serializer);
				break;
			}
		}
	}

}
