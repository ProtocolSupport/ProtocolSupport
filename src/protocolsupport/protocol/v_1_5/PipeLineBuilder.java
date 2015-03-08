package protocolsupport.protocol.v_1_5;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R2.NetworkManager;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ChannelHandlers;
import protocolsupport.protocol.IPipeLineBuilder;
import protocolsupport.protocol.v_1_5.clientboundtransformer.PacketEncoder;
import protocolsupport.protocol.v_1_5.serverboundtransformer.PacketDecoder;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public DecoderEncoderTuple buildPipeLine(Channel channel, ProtocolVersion version) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		PacketDecoder decoder = new PacketDecoder(version);
		networkmanager.a(new HandshakeListener(decoder, networkmanager));
		pipeline.remove(ChannelHandlers.SPLITTER);
		pipeline.remove(ChannelHandlers.PREPENDER);
		ChannelHandler encoder = new PacketEncoder(version);
		pipeline.replace(ChannelHandlers.DECODER, ChannelHandlers.DECODER, decoder);
		pipeline.replace(ChannelHandlers.ENCODER, ChannelHandlers.ENCODER, encoder);
		return new DecoderEncoderTuple(decoder, encoder);
	}

}
