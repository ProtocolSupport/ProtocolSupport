package com.github.shevchik.protocolsupport.protocol.transformer.v_1_6.serverboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import com.github.shevchik.protocolsupport.protocol.PacketDataSerializer;

public interface PacketTransformer {

	public Packet<PacketListener>[] tranform(Channel channel, int packetId, PacketDataSerializer serializer, PacketDataSerializer packetdata) throws IOException, IllegalAccessException, InstantiationException;

}
