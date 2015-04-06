package protocolsupport.protocol.pipeline.wrapped;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;
import protocolsupport.protocol.pipeline.PublicPacketEncoder;

public class WrappedEncoder extends MessageToByteEncoder<Packet<PacketListener>> {

	private PublicPacketEncoder realEncoder;

	public void setRealEncoder(PublicPacketEncoder realEncoder) {
		this.realEncoder = realEncoder;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		realEncoder.publicEncode(ctx, packet, output);
	}

}
