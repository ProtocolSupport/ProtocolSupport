package com.github.shevchik.protocolsupport.protocol.transformer.v_1_8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R3.NetworkManager;
import com.github.shevchik.protocolsupport.api.ProtocolVersion;
import com.github.shevchik.protocolsupport.protocol.core.ChannelHandlers;
import com.github.shevchik.protocolsupport.protocol.core.IPipeLineBuilder;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, ProtocolVersion version) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		networkmanager.a(new HandshakeListener(networkmanager));
		ChannelHandlers.getSplitter(pipeline).setRealSplitter(new PacketSplitter());
		ChannelHandlers.getPrepender(pipeline).setRealPrepender(new PacketPrepender());
		ChannelHandlers.getDecoder(pipeline).setRealDecoder(new PacketDecoder());
		ChannelHandlers.getEncoder(pipeline).setRealEncoder(new PacketEncoder());
	}

}
