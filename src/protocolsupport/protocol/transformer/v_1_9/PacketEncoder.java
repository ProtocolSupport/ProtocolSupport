package protocolsupport.protocol.transformer.v_1_9;

import java.io.IOException;
import java.lang.reflect.Field;

import org.spigotmc.SneakyThrow;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_9_R1.EnumProtocol;
import net.minecraft.server.v1_9_R1.EnumProtocolDirection;
import net.minecraft.server.v1_9_R1.NetworkManager;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketEncoder;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.utils.netty.WrappingBuffer;

public class PacketEncoder implements IPacketEncoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;
	private static final Field versionField = ReflectionUtils.getField(net.minecraft.server.v1_9_R1.PacketDataSerializer.class, "version");

	private final WrappingBuffer wrappingBuffer = new WrappingBuffer();
	private final PacketDataSerializer serializer;
	public PacketEncoder(ProtocolVersion version) {
		serializer = new PacketDataSerializer(wrappingBuffer, version);
		try {
			versionField.set(serializer, version.getId());
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		final Integer packetId = currentProtocol.a(direction, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		wrappingBuffer.setBuf(output);
		serializer.writeVarInt(packetId);
		packet.b(serializer);
	}

}
