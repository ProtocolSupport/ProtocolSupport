package protocolsupport.protocol.v_1_5;

import protocolsupport.injector.ProtocolLibFixer;
import protocolsupport.protocol.fake.FakeDecoder;
import protocolsupport.protocol.fake.FakeEncoder;
import protocolsupport.protocol.fake.FakePrepender;
import protocolsupport.protocol.fake.FakeSplitter;
import protocolsupport.protocol.v_1_5.clientboundtransformer.PacketEncoder;
import protocolsupport.protocol.v_1_5.serverboundtransformer.PacketDecoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;

public class PipeLineBuilder {

	public static void buildPipeLine(ChannelHandlerContext ctx) {
		ChannelPipeline pipeline = ctx.channel().pipeline();
		NetworkManager networkmanager = pipeline.get(NetworkManager.class);
		PacketDecoder decoder = new PacketDecoder();
		networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), decoder, networkmanager));
		pipeline.remove(FakeSplitter.class);
		pipeline.remove(FakePrepender.class);
		ChannelHandler encoder = new PacketEncoder();
		pipeline.replace(FakeDecoder.class, "decoder", decoder);
		pipeline.replace(FakeEncoder.class, "encoder", encoder);
		ProtocolLibFixer.fixProtocolLib(pipeline, decoder, encoder);
	}

}
