package protocolsupport.zplatform.impl.spigot.injector.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.LogicHandler;
import protocolsupport.protocol.pipeline.common.RawPacketDataCaptureReceive;
import protocolsupport.protocol.pipeline.common.RawPacketDataCaptureSend;
import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.Utils;
import protocolsupport.utils.netty.ChannelInitializer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotFakePacketListener;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecoder;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedPrepender;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedSplitter;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotServerConnectionChannel extends ChannelInitializer {

	private static final boolean replaceDecoderEncoder = Utils.getJavaPropertyValue("replaceencoderdecoder", false, Boolean::parseBoolean);

	@Override
	protected void initChannel(Channel channel) {
		NetworkManagerWrapper networkmanager = ServerPlatform.get().getMiscUtils().getNetworkManagerFromChannel(channel);
		networkmanager.setPacketListener(new SpigotFakePacketListener());
		ConnectionImpl connection = new ConnectionImpl(networkmanager);
		connection.storeInChannel(channel);
		ProtocolStorage.addConnection(channel.remoteAddress(), connection);
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addAfter(SpigotChannelHandlers.READ_TIMEOUT, ChannelHandlers.INITIAL_DECODER, new InitialPacketDecoder());
		pipeline.addBefore(SpigotChannelHandlers.NETWORK_MANAGER, ChannelHandlers.LOGIC, new LogicHandler(connection));
		pipeline.remove("legacy_query");
		pipeline.replace(SpigotChannelHandlers.READ_TIMEOUT, SpigotChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30));
		pipeline.replace(SpigotChannelHandlers.SPLITTER, SpigotChannelHandlers.SPLITTER, new SpigotWrappedSplitter());
		pipeline.replace(SpigotChannelHandlers.PREPENDER, SpigotChannelHandlers.PREPENDER, new SpigotWrappedPrepender());
		pipeline.addAfter(SpigotChannelHandlers.PREPENDER, ChannelHandlers.RAW_CAPTURE_SEND, new RawPacketDataCaptureSend(connection));
		pipeline.addAfter(SpigotChannelHandlers.SPLITTER, ChannelHandlers.RAW_CAPTURE_RECEIVE, new RawPacketDataCaptureReceive(connection));
		if (replaceDecoderEncoder) {
			if (pipeline.get(SpigotChannelHandlers.DECODER).getClass().equals(net.minecraft.server.v1_12_R1.PacketDecoder.class)) {
				pipeline.replace(SpigotChannelHandlers.DECODER, SpigotChannelHandlers.DECODER, new SpigotPacketDecoder());
			}
			if (pipeline.get(SpigotChannelHandlers.ENCODER).getClass().equals(net.minecraft.server.v1_12_R1.PacketEncoder.class)) {
				pipeline.replace(SpigotChannelHandlers.ENCODER, SpigotChannelHandlers.ENCODER, new SpigotPacketEncoder());
			}
		}
	}

}
