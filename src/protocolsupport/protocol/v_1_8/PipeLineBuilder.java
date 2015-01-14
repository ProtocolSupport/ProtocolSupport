package protocolsupport.protocol.v_1_8;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R1.HandshakeListener;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketPrepender;
import net.minecraft.server.v1_8_R1.PacketSplitter;
import protocolsupport.protocol.ChannelHandlers;
import protocolsupport.protocol.IPipeLineBuilder;
import protocolsupport.protocol.ProtocolVersion;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public DecoderEncoderTuple buildPipeLine(Channel channel, ProtocolVersion version) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
		ChannelHandler decoder = new PacketDecoder();
		ChannelHandler encoder = new PacketEncoder();
		pipeline.replace(ChannelHandlers.SPLITTER, ChannelHandlers.SPLITTER, new PacketSplitter());
		pipeline.replace(ChannelHandlers.DECODER, ChannelHandlers.DECODER, decoder);
		pipeline.replace(ChannelHandlers.PREPENDER, ChannelHandlers.PREPENDER, new PacketPrepender());
		pipeline.replace(ChannelHandlers.ENCODER, ChannelHandlers.ENCODER, encoder);
		return new DecoderEncoderTuple(decoder, encoder);
	}

}
