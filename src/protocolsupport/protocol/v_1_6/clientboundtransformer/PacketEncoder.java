package protocolsupport.protocol.v_1_6.clientboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.server.v1_8_R1.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R1.PlayerInfoData;
import protocolsupport.injector.Utilities;
import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
        final Integer packetId = currentProtocol.a(direction, packet);
        if (packetId == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
		if (currentProtocol == EnumProtocol.PLAY) {
			if (packetId == 0x38) {
				if (!skipPlayerInfo.contains(packet)) {
					for (Packet rpacket : splitPlayerInfoPacket((PacketPlayOutPlayerInfo) packet)) {
						skipPlayerInfo.add(rpacket);
						channel.writeAndFlush(rpacket);
					}
					return;
				} else {
					skipPlayerInfo.remove(packet);
				}
			}
		}
		PacketDataSerializer serializer = new PacketDataSerializer(output, DataStorage.getVersion(channel.remoteAddress()));
		transformers[currentProtocol.ordinal()].tranform(channel, packetId, packet, serializer);
	}

	private HashSet<Packet> skipPlayerInfo = new HashSet<Packet>();

	@SuppressWarnings("unchecked")
	private List<Packet> splitPlayerInfoPacket(PacketPlayOutPlayerInfo packet) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		List<Packet> packets = new ArrayList<Packet>();
		EnumPlayerInfoAction action = (EnumPlayerInfoAction) Utilities.<Field>setAccessible(PacketPlayOutPlayerInfo.class.getDeclaredField("a")).get(packet);
		List<PlayerInfoData> datas = (List<PlayerInfoData>) Utilities.<Field>setAccessible(PacketPlayOutPlayerInfo.class.getDeclaredField("b")).get(packet);
		for (int i = 0; i < datas.size(); i++) {
			PacketPlayOutPlayerInfo newpacket = new PacketPlayOutPlayerInfo();
			Utilities.<Field>setAccessible(PacketPlayOutPlayerInfo.class.getDeclaredField("a")).set(newpacket, action);
			((List<PlayerInfoData>) Utilities.<Field>setAccessible(PacketPlayOutPlayerInfo.class.getDeclaredField("b")).get(newpacket)).add(datas.get(i));
			packets.add(newpacket);
		}
		return packets;
	}

}
