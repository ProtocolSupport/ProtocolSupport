//package protocolsupport.zplatform.impl.glowstone.injector;
//
//import com.flowpowered.network.Message;
//
//import io.netty.buffer.PooledByteBufAllocator;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelException;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.ChannelPipeline;
//import protocolsupport.protocol.ConnectionImpl;
//import protocolsupport.protocol.pipeline.ChannelHandlers;
//import protocolsupport.protocol.pipeline.common.LogicHandler;
//import protocolsupport.protocol.pipeline.common.RawPacketDataCaptureReceive;
//import protocolsupport.protocol.pipeline.common.RawPacketDataCaptureSend;
//import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
//import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
//import protocolsupport.protocol.storage.ProtocolStorage;
//import protocolsupport.utils.netty.ChannelInitializer;
//import protocolsupport.zplatform.impl.glowstone.GlowStoneMiscUtils;
//import protocolsupport.zplatform.impl.glowstone.network.GlowStoneChannelHandlers;
//import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
//import protocolsupport.zplatform.impl.glowstone.network.pipeline.GlowStoneFramingHandler;
//import protocolsupport.zplatform.impl.glowstone.network.pipeline.GlowStoneSyncTickerStarter;
//
//public class GlowStoneServerConnectionChannel extends ChannelInitializer {
//
//	@Override
//	protected void initChannel(Channel channel) throws Exception {
//		try {
//			channel.config().setOption(ChannelOption.IP_TOS, 0x18);
//		} catch (ChannelException e) {
//		}
//		ChannelPipeline pipeline = channel.pipeline();
//		channel.config().setAllocator(PooledByteBufAllocator.DEFAULT);
//		ConnectionImpl connection = new ConnectionImpl(new GlowStoneNetworkManagerWrapper(GlowStoneMiscUtils.getNetworkManager(pipeline)));
//		connection.storeInChannel(channel);
//		ProtocolStorage.addConnection(channel.remoteAddress(), connection);
//		pipeline.remove(GlowStoneChannelHandlers.READ_TIMEOUT);
//		pipeline.remove(GlowStoneChannelHandlers.LEGACY_PING);
//		pipeline.remove(GlowStoneChannelHandlers.ENCRYPTION);
//		pipeline.remove(GlowStoneChannelHandlers.COMPRESSION);
//		pipeline.addFirst(GlowStoneChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30));
//		pipeline.addAfter(GlowStoneChannelHandlers.READ_TIMEOUT, ChannelHandlers.INITIAL_DECODER, new InitialPacketDecoder());
//		pipeline.addBefore(GlowStoneChannelHandlers.NETWORK_MANAGER, "ps_glowstone_sync_ticker", GlowStoneSyncTickerStarter.INSTANCE);
//		pipeline.addBefore(GlowStoneChannelHandlers.NETWORK_MANAGER, ChannelHandlers.LOGIC, new LogicHandler(connection, Message.class));
//		pipeline.replace(GlowStoneChannelHandlers.FRAMING, GlowStoneChannelHandlers.FRAMING, new GlowStoneFramingHandler());
//		pipeline.addAfter(GlowStoneChannelHandlers.FRAMING, ChannelHandlers.RAW_CAPTURE_SEND, new RawPacketDataCaptureSend(connection));
//		pipeline.addAfter(GlowStoneChannelHandlers.FRAMING, ChannelHandlers.RAW_CAPTURE_RECEIVE, new RawPacketDataCaptureReceive(connection));
//	}
//
//}
