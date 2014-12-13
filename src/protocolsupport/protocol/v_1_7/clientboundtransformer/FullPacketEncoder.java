package protocolsupport.protocol.v_1_7.clientboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
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

public class FullPacketEncoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	private static boolean[] blockedPlayPackets = new boolean[256];
	static {
		//packet from 1.8
		for (int i = 0x41; i < 0x49; i++) {
			blockedPlayPackets[i] = true;
		}
		//map packet
		blockedPlayPackets[0x34] = true;
	}

	public static void encodePacket(Channel channel, Packet packet, ByteBuf output) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer(), DataStorage.getVersion(channel.remoteAddress()));
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
        final Integer packetId = currentProtocol.a(direction, packet);
        if (packetId == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
		if (currentProtocol == EnumProtocol.PLAY) {
			if (blockedPlayPackets[packetId]) {
				return;
			} else if (packetId == 0x38) {
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
		serializer.writeVarInt(packetId);
		boolean needsWrite = !transformers[currentProtocol.ordinal()].tranform(channel, packetId, packet, serializer);
		if (needsWrite) {
			packet.b(serializer);
		}
		writeVarInt(serializer.readableBytes(), output);
		output.writeBytes(serializer);
	}

	private static void writeVarInt(int value, ByteBuf buf) {
        while ((value & 0xFFFFFF80) != 0x0) {
        	buf.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        buf.writeByte(value);
	}

	private static HashSet<Packet> skipPlayerInfo = new HashSet<Packet>();

	@SuppressWarnings("unchecked")
	private static List<Packet> splitPlayerInfoPacket(PacketPlayOutPlayerInfo packet) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
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
