package protocolsupport.protocol.transformer.v_1_6.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.protocol.PacketDataSerializer;

public class LoginPacketTransformer implements PacketTransformer {

	@SuppressWarnings("unchecked")
	@Override
	public Packet<PacketListener>[] tranform(Channel channel, int packetId, PacketDataSerializer serializer, PacketDataSerializer packetdata) throws IOException, IllegalAccessException, InstantiationException {
		switch (packetId) {
			case 0xFC: { //PacketLoginInEncryptionBegin
				Packet<PacketListener> packet = getPacketById(0x01);
				packet.a(serializer);
				return new Packet[] {packet};
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Packet<PacketListener> getPacketById(int realPacketId) throws IllegalAccessException, InstantiationException {
		return EnumProtocol.LOGIN.a(EnumProtocolDirection.SERVERBOUND, realPacketId);
	}

}
