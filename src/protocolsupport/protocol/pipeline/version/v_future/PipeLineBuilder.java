package protocolsupport.protocol.pipeline.version.v_future;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.handler.common.ModernHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.utils.netty.ChannelUtils;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = ChannelUtils.getNetworkManager(channel);
		networkmanager.setPacketListener(new ModernHandshakeListener(networkmanager, true));
		ChannelHandlers.getSplitter(pipeline).setRealSplitter(new VarIntFrameDecoder());
		ChannelHandlers.getPrepender(pipeline).setRealPrepender(new VarIntFrameEncoder());
	}

}
