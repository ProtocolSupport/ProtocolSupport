package protocolsupport.protocol.transformer.v_1_6;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R2.NetworkManager;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.transformer.v_1_6.clientboundtransformer.PacketEncoder;
import protocolsupport.protocol.transformer.v_1_6.serverboundtransformer.PacketDecoder;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, ProtocolVersion version) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		PacketDecoder decoder = new PacketDecoder(version);
		networkmanager.a(new HandshakeListener(decoder, networkmanager));
		pipeline.remove(ChannelHandlers.SPLITTER);
		pipeline.remove(ChannelHandlers.PREPENDER);
		ChannelHandlers.getDecoder(pipeline).setRealDecoder(decoder);
		ChannelHandlers.getEncoder(pipeline).setRealEncoder(new PacketEncoder(version));
	}

}
