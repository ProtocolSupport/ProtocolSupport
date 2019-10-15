package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.server.v1_14_R1.EnumProtocol;
import net.minecraft.server.v1_14_R1.EnumProtocolDirection;
import net.minecraft.server.v1_14_R1.NetworkManager;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketDataSerializer;
import net.minecraft.server.v1_14_R1.PacketListener;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.WrappingBuffer;

public class SpigotPacketEncoder extends MessageToByteEncoder<Packet<PacketListener>> {

	public SpigotPacketEncoder() {
		super(false);
	}

	private final WrappingBuffer wrapper = new WrappingBuffer();
	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(wrapper);

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf data) throws Exception {
		EnumProtocol currentProtocol = ctx.channel().attr(NetworkManager.c).get();
		final Integer packetId = currentProtocol.a(EnumProtocolDirection.CLIENTBOUND, packet);
		if (packetId == null) {
			throw new EncoderException("Can't serialize unregistered packet " + packet.getClass().getName());
		}
		wrapper.setBuf(data);
		VarNumberSerializer.writeVarInt(wrapper, packetId);
		try {
			packet.b(nativeSerializer);
		} catch (IOException e) {
			throw new EncoderException(e);
		}
	}

}
