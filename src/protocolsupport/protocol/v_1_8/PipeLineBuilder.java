package protocolsupport.protocol.v_1_8;

import protocolsupport.injector.ProtocolLibFixer;
import protocolsupport.protocol.fake.FakeDecoder;
import protocolsupport.protocol.fake.FakeEncoder;
import protocolsupport.protocol.fake.FakePrepender;
import protocolsupport.protocol.fake.FakeSplitter;
import net.minecraft.server.v1_8_R1.HandshakeListener;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketPrepender;
import net.minecraft.server.v1_8_R1.PacketSplitter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

public class PipeLineBuilder {

	public static void buildPipeLine(ChannelHandlerContext ctx) {
		ChannelPipeline pipeline = ctx.channel().pipeline();
		NetworkManager networkmanager = pipeline.get(NetworkManager.class);
		networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
		ChannelHandler decoder = new PacketDecoder();
		ChannelHandler encoder = new PacketEncoder();
		pipeline.replace(FakeSplitter.class, "splitter", new PacketSplitter());
		pipeline.replace(FakeDecoder.class, "decoder", decoder);
		pipeline.replace(FakePrepender.class, "prepender", new PacketPrepender());
		pipeline.replace(FakeEncoder.class, "encoder", encoder);
		ProtocolLibFixer.fixProtocolLib(pipeline, decoder, encoder);
	}

}
