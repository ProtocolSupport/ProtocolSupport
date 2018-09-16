package protocolsupport.protocol.pipeline.version.util.decoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.typeremapper.packet.AnimatePacketReorderer;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class AbstractModernWithReorderPacketDecoder extends AbstractModernPacketDecoder {

	public AbstractModernWithReorderPacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

	protected final AnimatePacketReorderer animateReorderer = new AnimatePacketReorderer();

	@Override
	protected RecyclableCollection<ServerBoundPacketData> processPackets(Channel channel, RecyclableCollection<ServerBoundPacketData> data) {
		return animateReorderer.orderPackets(data);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		animateReorderer.release();
	}

}
