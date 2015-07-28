package com.github.shevchik.protocolsupport.protocol.transformer.v_1_6.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import com.github.shevchik.protocolsupport.protocol.PacketDataSerializer;

public class LoginPacketTransformer implements PacketTransformer {

	@SuppressWarnings("unchecked")
	@Override
	public Packet<PacketListener>[] tranform(Channel channel, int packetId, PacketDataSerializer serializer, PacketDataSerializer packetdata) throws IOException, IllegalAccessException, InstantiationException {
		Packet<PacketListener> packet = null;
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

	@SuppressWarnings("unchecked")
	private Packet<PacketListener> getPacketById(int realPacketId) throws IllegalAccessException, InstantiationException {
		return EnumProtocol.LOGIN.a(EnumProtocolDirection.SERVERBOUND, realPacketId);
	}

}
