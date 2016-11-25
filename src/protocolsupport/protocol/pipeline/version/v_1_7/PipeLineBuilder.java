package protocolsupport.protocol.pipeline.version.v_1_7;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.handler.common.ModernHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.nms.NetworkManagerWrapper;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManagerWrapper networkmanager = NetworkManagerWrapper.getFromChannel(channel);
		networkmanager.setPacketListener(new ModernHandshakeListener(networkmanager, false));
		ChannelHandlers.getSplitter(pipeline).setRealSplitter(new VarIntFrameDecoder());
		ChannelHandlers.getPrepender(pipeline).setRealPrepender(new VarIntFrameEncoder());
		NetworkDataCache sharedstorage = new NetworkDataCache();
		pipeline.addAfter(ChannelHandlers.SPLITTER, ChannelHandlers.DECODER_TRANSFORMER, new PacketDecoder(connection, sharedstorage));
		pipeline.addAfter(ChannelHandlers.PREPENDER, ChannelHandlers.ENCODER_TRANSFORMER, new PacketEncoder(connection, sharedstorage));
	}

}
