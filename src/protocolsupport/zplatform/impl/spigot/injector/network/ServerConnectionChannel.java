package protocolsupport.zplatform.impl.spigot.injector.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.LogicHandler;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.pipeline.timeout.SimpleReadTimeoutHandler;
import protocolsupport.protocol.pipeline.wrapped.WrappedPrepender;
import protocolsupport.protocol.pipeline.wrapped.WrappedSplitter;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.spigot.SpigotConnectionImpl;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotFakePacketListener;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecoder;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class ServerConnectionChannel extends ChannelInitializer<Channel> {

	private static final boolean replaceDecoderEncoder = Utils.getJavaPropertyValue("replaceencoderdecoder", false, Converter.STRING_TO_BOOLEAN);

	@Override
	protected void initChannel(Channel channel) {
		NetworkManagerWrapper networkmanager = ServerPlatform.get().getMiscUtils().getNetworkManagerFromChannel(channel);
		networkmanager.setPacketListener(new SpigotFakePacketListener());
		SpigotConnectionImpl connection = new SpigotConnectionImpl(networkmanager);
		connection.storeInChannel(channel);
		ProtocolStorage.setConnection(channel.remoteAddress(), connection);
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addAfter(ChannelHandlers.READ_TIMEOUT, ChannelHandlers.INITIAL_DECODER, new InitialPacketDecoder());
		pipeline.addBefore(ChannelHandlers.NETWORK_MANAGER, ChannelHandlers.LOGIC, new LogicHandler(connection));
		pipeline.remove("legacy_query");
		pipeline.replace(ChannelHandlers.READ_TIMEOUT, ChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30));
		pipeline.replace(ChannelHandlers.SPLITTER, ChannelHandlers.SPLITTER, new WrappedSplitter());
		pipeline.replace(ChannelHandlers.PREPENDER, ChannelHandlers.PREPENDER, new WrappedPrepender());
		if (replaceDecoderEncoder) {
			if (pipeline.get(ChannelHandlers.DECODER).getClass().equals(net.minecraft.server.v1_11_R1.PacketDecoder.class)) {
				pipeline.replace(ChannelHandlers.DECODER, ChannelHandlers.DECODER, new SpigotPacketDecoder());
			}
			if (pipeline.get(ChannelHandlers.ENCODER).getClass().equals(net.minecraft.server.v1_11_R1.PacketEncoder.class)) {
				pipeline.replace(ChannelHandlers.ENCODER, ChannelHandlers.ENCODER, new SpigotPacketEncoder());
			}
		}
	}

}
