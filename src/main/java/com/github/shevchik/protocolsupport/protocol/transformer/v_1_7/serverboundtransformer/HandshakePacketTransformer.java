package com.github.shevchik.protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import com.github.shevchik.protocolsupport.api.ProtocolVersion;
import com.github.shevchik.protocolsupport.protocol.PacketDataSerializer;

public class HandshakePacketTransformer implements PacketTransformer {

	@Override
	public boolean tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer, PacketDataSerializer packetdata) throws IOException {
		serializer.readVarInt();
		packetdata.writeVarInt(ProtocolVersion.MINECRAFT_1_8.getId());
		packetdata.writeString(serializer.readString(32767));
		packetdata.writeShort(serializer.readUnsignedShort());
		packetdata.writeVarInt(serializer.readVarInt());
		packet.a(packetdata);
		return true;
	}

}
