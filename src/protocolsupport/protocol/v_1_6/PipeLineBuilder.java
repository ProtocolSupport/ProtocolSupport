package protocolsupport.protocol.v_1_6;

import protocolsupport.protocol.fake.FakeDecoder;
import protocolsupport.protocol.fake.FakeEncoder;
import protocolsupport.protocol.fake.FakePrepender;
import protocolsupport.protocol.fake.FakeSplitter;
import protocolsupport.protocol.v_1_6.clientboundtransformer.PacketEncoder;
import protocolsupport.protocol.v_1_6.serverboundtransformer.PacketDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;

public class PipeLineBuilder {

	public static void buildPipeLine(ChannelHandlerContext ctx, ByteBuf data) {
		ChannelPipeline pipeline = ctx.channel().pipeline();
		NetworkManager networkmanager = pipeline.get(NetworkManager.class);
		networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
		pipeline.remove(FakeSplitter.class);
		pipeline.remove(FakePrepender.class);
		pipeline.replace(FakeDecoder.class, "decoder", new PacketDecoder());
		pipeline.replace(FakeEncoder.class, "encoder", new PacketEncoder());
		ctx.channel().pipeline().firstContext().fireChannelRead(data);
	}

}
