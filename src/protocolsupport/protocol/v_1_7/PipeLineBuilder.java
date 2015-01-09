package protocolsupport.protocol.v_1_7;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketPrepender;
import net.minecraft.server.v1_8_R1.PacketSplitter;
import protocolsupport.protocol.ChannelHandlers;
import protocolsupport.protocol.IPipeLineBuilder;
import protocolsupport.protocol.ProtocolVersion;
import protocolsupport.protocol.v_1_7.clientboundtransformer.PacketEncoder;
import protocolsupport.protocol.v_1_7.serverboundtransformer.PacketDecoder;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public DecoderEncoderTuple buildPipeLine(ChannelHandlerContext ctx, ProtocolVersion version) {
		ChannelPipeline pipeline = ctx.channel().pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		networkmanager.a(new HandshakeListener(networkmanager, version));
		ChannelHandler decoder = new PacketDecoder(version);
		ChannelHandler encoder = new PacketEncoder(version);
		pipeline.replace(ChannelHandlers.SPLITTER, ChannelHandlers.SPLITTER, new PacketSplitter());
		pipeline.replace(ChannelHandlers.DECODER, ChannelHandlers.DECODER, decoder);
		pipeline.replace(ChannelHandlers.PREPENDER, ChannelHandlers.PREPENDER, new PacketPrepender());
		pipeline.replace(ChannelHandlers.ENCODER, ChannelHandlers.ENCODER, encoder);
		return new DecoderEncoderTuple(decoder, encoder);
	}

}
