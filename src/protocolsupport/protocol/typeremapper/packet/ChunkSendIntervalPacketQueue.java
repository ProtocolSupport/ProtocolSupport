package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO.IPacketDataProcessor;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO.IPacketDataProcessorContext;
import protocolsupport.utils.JavaSystemProperty;

public class ChunkSendIntervalPacketQueue implements IPacketDataProcessor {

	protected static final long chunkSendInterval = TimeUnit.MILLISECONDS.toNanos(JavaSystemProperty.getValue("chunksend18interval", 5L, Long::parseLong));

	public static boolean isEnabled() {
		return chunkSendInterval > 0;
	}

	protected static boolean shouldLock(ClientBoundPacketData packet) {
		return packet.getPacketType() == ClientBoundPacketType.PLAY_CHUNK_DATA;
	}

	protected boolean locked = false;
	protected final ArrayDeque<ClientBoundPacketData> queue = new ArrayDeque<>(1024);

	protected void clearQueue() {
		queue.forEach(PacketData::release);
		queue.clear();
	}

	@Override
	public void processClientbound(IPacketDataProcessorContext ctx, ClientBoundPacketData packetdata) {
		if (locked) {
			switch (packetdata.getPacketType()) {
				case PLAY_RESPAWN: {
					clearQueue();
					ctx.writeClientbound(packetdata);
					break;
				}
				case PLAY_ENTITY_PASSENGERS: {
					queue.add(packetdata.clone());
					ctx.writeClientbound(packetdata);
					break;
				}
				case PLAY_CHUNK_DATA:
				case PLAY_CHUNK_UNLOAD:
				case PLAY_BLOCK_CHANGE_SINGLE:
				case PLAY_BLOCK_CHANGE_MULTI:
				case PLAY_BLOCK_ACTION:
				case PLAY_BLOCK_BREAK_ANIMATION:
				case PLAY_BLOCK_TILE:
				case LEGACY_PLAY_UPDATE_SIGN:
				case LEGACY_PLAY_USE_BED: {
					queue.add(packetdata);
					break;
				}
				default: {
					ctx.writeClientbound(packetdata);
					break;
				}
			}
		} else {
			ctx.writeClientbound(packetdata);
			if (shouldLock(packetdata)) {
				lock(ctx);
			}
		}
	}

	protected void lock(IPacketDataProcessorContext ctx) {
		locked = true;
		ctx.getIOExecutor().schedule(
			() -> {
				locked = false;
				if (!queue.isEmpty()) {
					ClientBoundPacketData qPacket = null;
					while ((qPacket = queue.pollFirst()) != null) {
						ctx.writeClientbound(qPacket);
						if (shouldLock(qPacket)) {
							lock(ctx);
						}
					}
				}
			},
			chunkSendInterval, TimeUnit.NANOSECONDS
		);
	}

	@Override
	public void processorRemoved(IPacketDataProcessorContext ctx) {
		clearQueue();
	}

}
