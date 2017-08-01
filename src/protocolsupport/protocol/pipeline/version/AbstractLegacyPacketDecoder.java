package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyAnimatePacketReorderer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.zplatform.ServerPlatform;

public class AbstractLegacyPacketDecoder extends AbstractPacketDecoder {

	public AbstractLegacyPacketDecoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

	private final ByteBuf buffer = Unpooled.buffer();
	private final ReplayingDecoderBuffer cumulation = new ReplayingDecoderBuffer(buffer);
	private final LegacyAnimatePacketReorderer animateReorderer = new LegacyAnimatePacketReorderer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		buffer.writeBytes(input);
		while (buffer.isReadable() && decode0(ctx.channel(), list)) {}
		buffer.discardSomeReadBytes();
	}

	private boolean decode0(Channel channel, List<Object> list) throws Exception {
		cumulation.markReaderIndex();
		ServerBoundMiddlePacket packetTransformer = null;
		try {
			packetTransformer = registry.getTransformer(ServerPlatform.get().getMiscUtils().getNetworkStateFromChannel(channel), cumulation.readUnsignedByte());
			packetTransformer.readFromClientData(cumulation);
			addPackets(animateReorderer.orderPackets(packetTransformer.toNative()), list);
			return true;
		} catch (EOFSignal ex) {
			cumulation.resetReaderIndex();
			return false;
		} catch (Exception e) {
			throwFailedTransformException(e, packetTransformer, cumulation);
			return false;
		}
	}

}
