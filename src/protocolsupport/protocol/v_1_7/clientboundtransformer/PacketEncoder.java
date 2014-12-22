package protocolsupport.protocol.v_1_7.clientboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R1.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R1.PlayerInfoData;
import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.DataStorage.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.Utils;

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

	private boolean[] blockedPlayPackets = new boolean[256];
	{
		//packet from 1.8
		for (int i = 0x41; i < 0x49; i++) {
			blockedPlayPackets[i] = true;
		}
		//map packet
		blockedPlayPackets[0x34] = true;
	}

	@SuppressWarnings("unchecked")
	private List<Packet> splitPlayerInfoPacket(PacketPlayOutPlayerInfo packet) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		List<Packet> packets = new ArrayList<Packet>();
		List<PlayerInfoData> datas = (List<PlayerInfoData>) Utils.<Field>setAccessible(PacketPlayOutPlayerInfo.class.getDeclaredField("b")).get(packet);
		if (datas.size() <= 1) {
			return null;
		}
		EnumPlayerInfoAction action = (EnumPlayerInfoAction) Utils.<Field>setAccessible(PacketPlayOutPlayerInfo.class.getDeclaredField("a")).get(packet);
		for (int i = 0; i < datas.size(); i++) {
			PacketPlayOutPlayerInfo newpacket = new PacketPlayOutPlayerInfo();
			Utils.<Field>setAccessible(PacketPlayOutPlayerInfo.class.getDeclaredField("a")).set(newpacket, action);
			((List<PlayerInfoData>) Utils.<Field>setAccessible(PacketPlayOutPlayerInfo.class.getDeclaredField("b")).get(newpacket)).add(datas.get(i));
			packets.add(newpacket);
		}
		return packets;
	}

	private List<Packet> splitEntityDestroyPacket(Packet packet) throws IOException {
		List<Packet> packets = new ArrayList<Packet>();
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_1_8);
		packet.b(packetdata);
		int count = packetdata.readVarInt();
		if (count <= 120) {
			return null;
		}
		int[] array = new int[count];
		for (int i = 0; i < count; i++) {
			array[i] = packetdata.readVarInt();
		}
		for (int[] part : Utils.splitArray(array, 120)) {
			packets.add(new PacketPlayOutEntityDestroy(part));
		}
		return packets;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
        final Integer packetId = currentProtocol.a(direction, packet);
        if (packetId == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
		if (currentProtocol == EnumProtocol.PLAY) {
			if (blockedPlayPackets[packetId]) {
				return;
			} else if (packetId == 0x38) {
				List<Packet> splitPackets = splitPlayerInfoPacket((PacketPlayOutPlayerInfo) packet);
				if (splitPackets != null) {
					for (Packet rpacket : splitPackets) {
						channel.writeAndFlush(rpacket);
					}
					return;
				}
			} else if (packetId == 0x13) {
				List<Packet> splitPackets = splitEntityDestroyPacket(packet);
				if (splitPackets != null) {
					for (Packet rpacket : splitPackets) {
						channel.writeAndFlush(rpacket);
					}
					return;
				}
			}
		}
		PacketDataSerializer serializer = new PacketDataSerializer(output, DataStorage.getVersion(channel.remoteAddress()));
		transformers[currentProtocol.ordinal()].tranform(channel, packetId, packet, serializer);
	}

}
