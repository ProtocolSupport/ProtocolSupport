package protocolsupport.protocol.pipeline.version.v_1_5;

import io.netty.channel.Channel;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.version.util.builder.AbstractNoopFramingPipeLineBuilder;
import protocolsupport.protocol.typeremapper.packet.AnimatePacketReorderer;

public class PipeLineBuilder extends AbstractNoopFramingPipeLineBuilder {

	@Override
	public void buildCodec(Channel channel, ConnectionImpl connection) {
		connection.initCodec(PacketCodec.instance);
		connection.getCodec().addServerboundPacketProcessor(new AnimatePacketReorderer());
		channel.pipeline()
		.addAfter(ChannelHandlers.RAW_CAPTURE_RECEIVE, ChannelHandlers.DECODER_TRANSFORMER, new PacketDecoder(connection))
		.addAfter(ChannelHandlers.RAW_CAPTURE_SEND, ChannelHandlers.ENCODER_TRANSFORMER, new PacketEncoder(connection));
	}

}
