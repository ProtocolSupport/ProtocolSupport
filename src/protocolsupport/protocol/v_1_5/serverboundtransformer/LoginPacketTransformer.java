package protocolsupport.protocol.v_1_5.serverboundtransformer;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;

public class LoginPacketTransformer implements PacketTransformer {

	@Override
	public Packet[] tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		Packet packet = null;
		switch (packetId) {
			case 0xFC: { //PacketLoginInEncryptionBegin
				packet = getPacketById(0x01);
				int length1 = serializer.readUnsignedShort();
				packetdata.writeVarInt(length1);
				packetdata.writeBytes(serializer.readBytes(length1));
				int length2 = serializer.readUnsignedShort();
				packetdata.writeVarInt(length2);
				packetdata.writeBytes(serializer.readBytes(length2));
				break;
			}
		}
		if (packet != null) {
			packet.a(packetdata);
			return new Packet[] {packet};
		}
		return null;
	}

	private Packet getPacketById(int realPacketId) {
		return EnumProtocol.LOGIN.a(EnumProtocolDirection.SERVERBOUND, realPacketId);
	}

}
