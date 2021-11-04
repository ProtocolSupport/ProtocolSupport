package protocolsupport.protocol.pipeline;

import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public interface IPipelineBuilder {

	public IPacketIdCodec getPacketIdCodec();

	public void buildTransport(ChannelPipeline pipeline);

	public void buildCodec(ChannelPipeline pipeline, IPacketDataChannelIO io, NetworkDataCache cache);

	public default void buildPostProcessor(IPacketDataChannelIO io) {
	}

}
