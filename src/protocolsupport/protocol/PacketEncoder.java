package protocolsupport.protocol;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import protocolsupport.injector.Utilities;
import protocolsupport.protocol.clientboundtransformer.HandshakePacketTransformer;
import protocolsupport.protocol.clientboundtransformer.LoginPacketTransformer;
import protocolsupport.protocol.clientboundtransformer.PacketTransformer;
import protocolsupport.protocol.clientboundtransformer.PlayPacketTransformer;
import protocolsupport.protocol.clientboundtransformer.StatusPacketTransformer;
import net.minecraft.server.v1_8_R1.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R1.PlayerInfoData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	private PacketTransformer[] transformers = new PacketTransformer[] {
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

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf bytebuf) throws Exception {
		try {
		EnumProtocol currentProtocol = channelHandlerContext.channel().attr(currentStateAttrKey).get();
        final Integer packetId = currentProtocol.a(direction, packet);
        if (packetId == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
		int version = ServerConnectionChannel.getVersion(channelHandlerContext.channel().remoteAddress());
		if (version != ServerConnectionChannel.CLIENT_1_8_PROTOCOL_VERSION && currentProtocol == EnumProtocol.PLAY) {
			if (blockedPlayPackets[packetId]) {
				return;
			} else if (packetId == 0x38) {
				if (!skipPlayerInfo.contains(packet)) {
					for (Packet rpacket : splitPlayerInfoPacket((PacketPlayOutPlayerInfo) packet)) {
						skipPlayerInfo.add(rpacket);
						channelHandlerContext.channel().writeAndFlush(rpacket);
					}
					return;
				} else {
					skipPlayerInfo.remove(packet);
				}
			}
		}
        final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(bytebuf, version);
        packetDataSerializer.b(packetId);
		boolean needsWrite = !transformers[currentProtocol.ordinal()].tranform(channelHandlerContext.channel(), packetId, packet, packetDataSerializer);
		if (needsWrite) {
			packet.b(packetDataSerializer);
		}
		} catch (Throwable t) {
			t.printStackTrace();
		}
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
