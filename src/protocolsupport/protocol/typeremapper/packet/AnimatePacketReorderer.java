package protocolsupport.protocol.typeremapper.packet;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO.IPacketDataProcessor;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO.IPacketDataProcessorContext;

public class AnimatePacketReorderer implements IPacketDataProcessor {

	protected ServerBoundPacketData animationPacket;

	@Override
	public void processServerbound(IPacketDataProcessorContext ctx, ServerBoundPacketData packetdata) {
		ServerBoundPacketType packetType = packetdata.getPacketType();

		if (animationPacket != null) {
			if (packetType == ServerBoundPacketType.PLAY_USE_ENTITY) {
				ctx.writeServerbound(packetdata);
				ctx.writeServerbound(animationPacket);
				animationPacket = null;
				return;
			} else {
				ctx.writeServerbound(animationPacket);
				animationPacket = null;
			}
		}

		if (packetType == ServerBoundPacketType.PLAY_ANIMATION) {
			animationPacket = packetdata;
		} else {
			ctx.writeServerbound(packetdata);
		}
	}

	@Override
	public void processorRemoved(IPacketDataProcessorContext ctx) {
		if (animationPacket != null) {
			animationPacket.release();
		}
	}

}
