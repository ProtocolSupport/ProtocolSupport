package protocolsupport.zplatform.impl.glowstone.injector;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.LogicHandler;
import protocolsupport.protocol.pipeline.common.RawPacketDataCaptureReceive;
import protocolsupport.protocol.pipeline.common.RawPacketDataCaptureSend;
import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.netty.ChannelInitializer;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneChannelHandlers;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
import protocolsupport.zplatform.impl.glowstone.network.pipeline.GlowStoneFramingHandler;
import protocolsupport.zplatform.impl.glowstone.network.pipeline.GlowStoneSyncConnectionTicker;

public class GlowStoneServerConnectionChannel extends ChannelInitializer {

	@Override
	protected void initChannel(Channel channel) throws Exception {
		try {
			channel.config().setOption(ChannelOption.IP_TOS, 0x18);
		} catch (ChannelException e) {
		}
		channel.config().setAllocator(PooledByteBufAllocator.DEFAULT);
		ConnectionImpl connection = new ConnectionImpl(GlowStoneNetworkManagerWrapper.getFromChannel(channel));
		connection.storeInChannel(channel);
		ProtocolStorage.addConnection(channel.remoteAddress(), connection);
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.remove(GlowStoneChannelHandlers.READ_TIMEOUT);
		pipeline.remove("legacy_ping");
		pipeline.remove("encryption");
		pipeline.remove("compression");
		pipeline.addFirst(GlowStoneChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30));
		pipeline.addAfter(GlowStoneChannelHandlers.READ_TIMEOUT, ChannelHandlers.INITIAL_DECODER, new InitialPacketDecoder());
		pipeline.addBefore(GlowStoneChannelHandlers.NETWORK_MANAGER, "ps_glowstone_sync_ticker", new GlowStoneSyncConnectionTicker());
		pipeline.addBefore(GlowStoneChannelHandlers.NETWORK_MANAGER, ChannelHandlers.LOGIC, new LogicHandler(connection));
		pipeline.replace(GlowStoneChannelHandlers.FRAMING, GlowStoneChannelHandlers.FRAMING, new GlowStoneFramingHandler());
		pipeline.addAfter(GlowStoneChannelHandlers.FRAMING, ChannelHandlers.RAW_CAPTURE_SEND, new RawPacketDataCaptureSend(connection));
		pipeline.addAfter(GlowStoneChannelHandlers.FRAMING, ChannelHandlers.RAW_CAPTURE_RECEIVE, new RawPacketDataCaptureReceive(connection));
	}

}
