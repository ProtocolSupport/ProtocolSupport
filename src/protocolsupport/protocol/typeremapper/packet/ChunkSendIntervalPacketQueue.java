package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

import protocolsupport.protocol.PacketDataCodecImpl.ClientBoundPacketDataProcessor;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.utils.JavaSystemProperty;

public class ChunkSendIntervalPacketQueue extends ClientBoundPacketDataProcessor {

	protected static boolean shouldLock(PacketData<?> packet) {
		return packet.getPacketType() == PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE;
	}
	protected static final long chunkSendInterval = TimeUnit.MILLISECONDS.toNanos(JavaSystemProperty.getValue("chunksend18interval", 5L, Long::parseLong));

	protected boolean locked = false;
	protected final ArrayDeque<PacketData<?>> queue = new ArrayDeque<>(1024);

	protected void clearQueue() {
		queue.forEach(PacketData::release);
		queue.clear();
	}

	@Override
	public void process(PacketData<?> packet) {
		if (locked) {
			switch (packet.getPacketType()) {
				case CLIENTBOUND_PLAY_RESPAWN: {
					clearQueue();
					write(packet);
					break;
				}
				case CLIENTBOUND_PLAY_ENTITY_PASSENGERS: {
					queue.add(packet.clone());
					write(packet);
					break;
				}
				case CLIENTBOUND_PLAY_CHUNK_SINGLE:
				case CLIENTBOUND_PLAY_CHUNK_UNLOAD:
				case CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE:
				case CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI:
				case CLIENTBOUND_PLAY_BLOCK_ACTION:
				case CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION:
				case CLIENTBOUND_PLAY_BLOCK_TILE:
				case CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN:
				case CLIENTBOUND_LEGACY_PLAY_USE_BED: {
					queue.add(packet);
					break;
				}
				default: {
					write(packet);
					break;
				}
			}
		} else {
			write(packet);
			if (shouldLock(packet)) {
				lock();
			}
		}
	}

	protected void lock() {
		locked = true;
		getIOExecutor().schedule(
			() -> {
				locked = false;
				if (!queue.isEmpty()) {
					PacketData<?> qPacket = null;
					while ((qPacket = queue.pollFirst()) != null) {
						write(qPacket);
						if (shouldLock(qPacket)) {
							lock();
						}
					}
				}
			},
			chunkSendInterval, TimeUnit.NANOSECONDS
		);
	}

	@Override
	public void release() {
		clearQueue();
	}

}
