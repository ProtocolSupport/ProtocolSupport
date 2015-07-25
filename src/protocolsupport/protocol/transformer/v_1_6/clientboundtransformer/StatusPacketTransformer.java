package protocolsupport.protocol.transformer.v_1_6.clientboundtransformer;

import io.netty.channel.Channel;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.ChatTypeAdapterFactory;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.ServerPing;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.ServerPingSerializers;
import protocolsupport.utils.Utils;

public class StatusPacketTransformer implements PacketTransformer {

	private static final Gson gson = new GsonBuilder()
	.registerTypeAdapter(ServerPing.ServerData.class, new ServerPingSerializers.ServerDataSerializer())
	.registerTypeAdapter(ServerPing.ServerPingPlayerSample.class, new ServerPingSerializers.PlayerSampleSerializer())
	.registerTypeAdapter(ServerPing.class, new ServerPing.Serializer())
	.registerTypeHierarchyAdapter(IChatBaseComponent.class,new IChatBaseComponent.ChatSerializer())
	.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer())
	.registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();

	@Override
	public void tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		if (packetId == 0x00) {
			PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), serializer.getVersion());
			packet.b(packetdata);
			ServerPing serverPing = gson.fromJson(packetdata.readString(32767), ServerPing.class);
			String response =
				"§1\u0000" +
				serializer.getVersion().getId() +
				"\u0000" +
				serverPing.c().a() +
				"\u0000" +
				Utils.fromComponent(serverPing.a()) +
				"\u0000" +
				serverPing.b().b() +
				"\u0000" +
				serverPing.b().a();
			serializer.writeByte(0xFF);
			serializer.writeString(response);
			packetdata.release();
		}
	}

}
