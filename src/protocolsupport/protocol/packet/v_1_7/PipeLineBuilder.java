package protocolsupport.protocol.packet.v_1_7;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.handler.common.ModernHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.protocol.storage.SharedStorage;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, ProtocolVersion version) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		networkmanager.setPacketListener(new ModernHandshakeListener(networkmanager, false));
		ChannelHandlers.getSplitter(pipeline).setRealSplitter(new VarIntFrameDecoder());
		ChannelHandlers.getPrepender(pipeline).setRealPrepender(new VarIntFrameEncoder());
		SharedStorage sharedstorage = new SharedStorage();
		ChannelHandlers.getDecoder(pipeline).setRealDecoder(new PacketDecoder(version, sharedstorage));
		ChannelHandlers.getEncoder(pipeline).setRealEncoder(new PacketEncoder(version, sharedstorage));
	}

}
