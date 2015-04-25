package protocolsupport.protocol.transformer.v_1_7.clientboundtransformer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.server.v1_8_R2.ChatModifier;
import net.minecraft.server.v1_8_R2.ChatTypeAdapterFactory;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;
import net.minecraft.server.v1_8_R2.ServerPing;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.ServerPingSerializers;

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
			PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
			packet.b(packetdata);
			ServerPing serverPing = gson.fromJson(packetdata.readString(32767), ServerPing.class);
			serverPing.setServerInfo(new ServerPing.ServerData(serverPing.c().a(), serializer.getVersion().getId()));
			serializer.writeVarInt(packetId);
			serializer.writeString(gson.toJson(serverPing));
			return;
		}
		serializer.writeVarInt(packetId);
		packet.b(serializer);
	}

}
