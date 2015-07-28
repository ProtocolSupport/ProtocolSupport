package com.github.shevchik.protocolsupport.protocol.transformer.v_1_7.clientboundtransformer;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.ChatTypeAdapterFactory;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.ServerPing;
import com.github.shevchik.protocolsupport.protocol.PacketDataSerializer;
import com.github.shevchik.protocolsupport.protocol.transformer.utils.ServerPingSerializers;
import com.github.shevchik.protocolsupport.utils.Allocator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StatusPacketTransformer implements PacketTransformer {

	private static final Gson gson = new GsonBuilder()
	.registerTypeAdapter(ServerPing.ServerData.class, new ServerPingSerializers.ServerDataSerializer())
	.registerTypeAdapter(ServerPing.ServerPingPlayerSample.class, new ServerPingSerializers.PlayerSampleSerializer())
	.registerTypeAdapter(ServerPing.class, new ServerPing.Serializer())
	.registerTypeHierarchyAdapter(IChatBaseComponent.class,new IChatBaseComponent.ChatSerializer())
	.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer())
	.registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();

	@Override
	public void tranform(ChannelHandlerContext ctx, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		if (packetId == 0x00) {
			PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), serializer.getVersion());
			packet.b(packetdata);
			ServerPing serverPing = gson.fromJson(packetdata.readString(32767), ServerPing.class);
			serverPing.setServerInfo(new ServerPing.ServerData(serverPing.c().a(), serializer.getVersion().getId()));
			serializer.writeVarInt(packetId);
			serializer.writeString(gson.toJson(serverPing));
			packetdata.release();
			return;
		}
		serializer.writeVarInt(packetId);
		packet.b(serializer);
	}

}
