package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.packet.PacketDataCodec.ClientBoundPacketDataProcessor;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.utils.JavaSystemProperty;

public class ChunkSendIntervalPacketQueue extends ClientBoundPacketDataProcessor {

	public ChunkSendIntervalPacketQueue(PacketDataCodec codec) {
		super(codec);
	}

	protected static final Set<PacketType> queuedPacketTypes = EnumSet.of(
		PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE, PacketType.CLIENTBOUND_PLAY_CHUNK_UNLOAD,
		PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE, PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI,
		PacketType.CLIENTBOUND_PLAY_BLOCK_ACTION, PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION,
		PacketType.CLIENTBOUND_PLAY_BLOCK_TILE, PacketType.CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN,
		PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED
	);
	protected static boolean shouldQueue(PacketData<?> packet) {
		return queuedPacketTypes.contains(packet.getPacketType());
	}
	protected static boolean shouldLock(PacketData<?> packet) {
		return packet.getPacketType() == PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE;
	}
	protected static final long chunkSendInterval = TimeUnit.MILLISECONDS.toNanos(JavaSystemProperty.getValue("chunksend18interval", 5L, Long::parseLong));

	protected boolean locked = false;
	protected final ArrayDeque<PacketData<?>> queue = new ArrayDeque<>(1024);

	@Override
	public void process(PacketData<?> packet) {
		if (locked) {
			if (packet.getPacketType() == PacketType.CLIENTBOUND_PLAY_ENTITY_PASSENGERS) {
				queue.add(packet.clone());
				write(packet);
			} else if (shouldQueue(packet)) {
				queue.add(packet);
			} else {
				write(packet);
			}
			return;
		}

		write(packet);
		if (shouldLock(packet)) {
			lock();
		}
	}

	protected boolean processQueue() {
		if (!queue.isEmpty()) {
			PacketData<?> qPacket = null;
			while ((qPacket = queue.pollFirst()) != null) {
				write(qPacket);
				if (shouldLock(qPacket)) {
					lock();
					return true;
				}
			}
		}
		return false;
	}

	protected void lock() {
		locked = true;
		codec.getConnection().getEventLoop().schedule(
			() -> {
				locked = false;
				processQueue();
			},
			chunkSendInterval, TimeUnit.NANOSECONDS
		);
	}

	protected static enum State {
		UNLOCKED, LOCKED;
	}

	@Override
	public void release() {
		queue.forEach(PacketData::release);
		queue.clear();
	}

}
