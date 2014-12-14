package protocolsupport.protocol.v_1_8;

import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.HandshakeListener;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketDecoder;
import net.minecraft.server.v1_8_R1.PacketEncoder;
import net.minecraft.server.v1_8_R1.PacketPrepender;
import net.minecraft.server.v1_8_R1.PacketSplitter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

public class PipeLineBuilder {

	public static void buildPipeLine(ChannelHandlerContext ctx, ByteBuf data) {
		ChannelPipeline pipeline = ctx.channel().pipeline();
		NetworkManager networkmanager = pipeline.get(NetworkManager.class);
		networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
		pipeline
		.addAfter("timeout", "splitter", new PacketSplitter())
		.addAfter("splitter", "decoder", new PacketDecoder(EnumProtocolDirection.SERVERBOUND))
		.addAfter("decoder", "prepender", new PacketPrepender())
		.addAfter("prepender", "encoder", new PacketEncoder(EnumProtocolDirection.CLIENTBOUND));
		ctx.channel().pipeline().firstContext().fireChannelRead(data);
	}

}
