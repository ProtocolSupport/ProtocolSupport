package protocolsupport.protocol.pipeline.version.util.decoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.pipeline.IPacketCodec;
import protocolsupport.protocol.typeremapper.packet.AnimatePacketReorderer;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class AbstractModernWithReorderPacketDecoder extends AbstractModernPacketDecoder {

	public AbstractModernWithReorderPacketDecoder(ConnectionImpl connection, IPacketCodec codec) {
		super(connection, codec);
	}

	protected final AnimatePacketReorderer animateReorderer = new AnimatePacketReorderer();

	@Override
	protected RecyclableCollection<? extends IPacketData> processPackets(Channel channel, RecyclableCollection<? extends IPacketData> data) {
		return animateReorderer.orderPackets(data);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		animateReorderer.release();
	}

}
