package protocolsupport.protocol.v_1_6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R1.NetworkManager;
import protocolsupport.protocol.ChannelHandlers;
import protocolsupport.protocol.IPipeLineBuilder;
import protocolsupport.protocol.ProtocolVersion;
import protocolsupport.protocol.v_1_6.clientboundtransformer.PacketEncoder;
import protocolsupport.protocol.v_1_6.serverboundtransformer.PacketDecoder;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public DecoderEncoderTuple buildPipeLine(ChannelHandlerContext ctx, ProtocolVersion version) {
		ChannelPipeline pipeline = ctx.channel().pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		PacketDecoder decoder = new PacketDecoder(version);
		networkmanager.a(new HandshakeListener(decoder, networkmanager, version));
		pipeline.remove(ChannelHandlers.SPLITTER);
		pipeline.remove(ChannelHandlers.PREPENDER);
		ChannelHandler encoder = new PacketEncoder(version);
		pipeline.replace(ChannelHandlers.DECODER, ChannelHandlers.DECODER, decoder);
		pipeline.replace(ChannelHandlers.ENCODER, ChannelHandlers.ENCODER, encoder);
		return new DecoderEncoderTuple(decoder, encoder);
	}

}
