package protocolsupport.protocol.core.wrapped;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_9_R1.EnumProtocolDirection;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PacketEncoder;
import net.minecraft.server.v1_9_R1.PacketListener;
import protocolsupport.protocol.core.IPacketEncoder;

public class WrappedEncoder extends PacketEncoder {

	public WrappedEncoder() {
		super(EnumProtocolDirection.CLIENTBOUND);
	}

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
