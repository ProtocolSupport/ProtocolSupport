package protocolsupport.protocol.pipeline.wrapped;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketListener;
import protocolsupport.protocol.pipeline.IPacketEncoder;

public class WrappedEncoder extends MessageToByteEncoder<Packet<PacketListener>> {

	private IPacketEncoder realEncoder = new IPacketEncoder() {
		@Override
		public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		}
	};

	public void setRealEncoder(IPacketEncoder realEncoder) {
		this.realEncoder = realEncoder;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf output) throws Exception {
		realEncoder.encode(ctx, packet, output);
	}

}
