package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.EnumProtocol;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.PacketListener;
import net.minecraft.network.SkipEncodeException;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.utils.netty.WrappingByteBuf;

public class SpigotPacketEncoder extends MessageToByteEncoder<Packet<PacketListener>> {

	public SpigotPacketEncoder() {
		super(false);
	}

	private final WrappingByteBuf wrapper = new WrappingByteBuf();
	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(wrapper);

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf data) throws Exception {
		EnumProtocol currentProtocol = ctx.channel().attr(NetworkManager.c).get();
		final Integer packetId = currentProtocol.a(EnumProtocolDirection.b, packet);
		if (packetId == null) {
			throw new EncoderException("Can't serialize unregistered packet " + packet.getClass().getName());
		}
		wrapper.setBuf(data);
        try {
			VarNumberCodec.writeVarInt(wrapper, packetId);
			packet.a(nativeSerializer);
            if (wrapper.readableBytes() > 2097152) {
                throw new IllegalArgumentException("Packet length varint length is more than 21 bits");
            }
		} catch (Throwable t) {
			Bukkit.getLogger().log(Level.SEVERE, "Error encoding packet", t);
			if (packet.a()) {
				throw new SkipEncodeException(t);
			}
			throw t;
		}
	}

}
