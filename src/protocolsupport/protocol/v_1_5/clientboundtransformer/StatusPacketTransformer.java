package protocolsupport.protocol.v_1_5.clientboundtransformer;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.ChatModifier;
import net.minecraft.server.v1_8_R1.ChatModifierSerializer;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.ChatTypeAdapterFactory;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.ServerPing;
import net.minecraft.server.v1_8_R1.ServerPingPlayerSample;
import net.minecraft.server.v1_8_R1.ServerPingPlayerSampleSerializer;
import net.minecraft.server.v1_8_R1.ServerPingSerializer;
import net.minecraft.server.v1_8_R1.ServerPingServerData;
import net.minecraft.server.v1_8_R1.ServerPingServerDataSerializer;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftChatMessage;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;

public class StatusPacketTransformer implements PacketTransformer {

	private static final Gson gson = new GsonBuilder()
	.registerTypeAdapter(ServerPingServerData.class, new ServerPingServerDataSerializer())
	.registerTypeAdapter(ServerPingPlayerSample.class, new ServerPingPlayerSampleSerializer())
	.registerTypeAdapter(ServerPing.class, new ServerPingSerializer())
	.registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer())
	.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifierSerializer())
	.registerTypeAdapterFactory(new ChatTypeAdapterFactory())
	.create();

	@Override
	public void tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		if (packetId == 0x00) {
			PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
			packet.b(packetdata);
			ServerPing serverPing = gson.fromJson(packetdata.readString(32767), ServerPing.class);
			String response =
				"§1\u0000" +
				DataStorage.getVersion(channel.remoteAddress()).getId() +
				"\u0000" +
				serverPing.c().a() +
				"\u0000" +
				CraftChatMessage.fromComponent(serverPing.a()) +
				"\u0000" +
				serverPing.b().b() +
				"\u0000" +
				serverPing.b().a();
			serializer.writeByte(0xFF);
			serializer.writeString(response);
		}
	}

}
