package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.ConnectionImpl.ClientboundPacketProcessor;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.utils.recyclable.Recyclable;

public class ChunkSendIntervalPacketQueue extends ClientboundPacketProcessor {

	public ChunkSendIntervalPacketQueue(ConnectionImpl connection) {
		super(connection);
	}

	//TODO: deal with entities
	protected static final IntOpenHashSet queuedPacketTypes = new IntOpenHashSet(new int[] {
		PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE.getId(), PacketType.CLIENTBOUND_PLAY_CHUNK_UNLOAD.getId(),
		PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE.getId(), PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI.getId(),
		PacketType.CLIENTBOUND_PLAY_BLOCK_ACTION.getId(), PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION.getId(),
		PacketType.CLIENTBOUND_PLAY_BLOCK_TILE.getId(), PacketType.CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN_ID.getId(),
		PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED_ID.getId()
	});
	protected static boolean shouldQueue(IPacketData packet) {
		return queuedPacketTypes.contains(packet.getPacketType().getId());
	}
	protected static boolean shouldLock(IPacketData packet) {
		return packet.getPacketType() == PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE;
	}
	protected static final long chunkSendInterval = TimeUnit.MILLISECONDS.toNanos(JavaSystemProperty.getValue("chunksend18interval", 5L, Long::parseLong));

	protected boolean locked = false;
	protected final ArrayDeque<IPacketData> queue = new ArrayDeque<>(1024);

	@Override
	public void process(IPacketData packet) {
		if (locked) {
			if (packet.getPacketType() == PacketType.CLIENTBOUND_PLAY_ENTITY_PASSENGERS) {
				System.err.println("ququq");
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
			IPacketData qPacket = null;
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
		connection.getEventLoop().schedule(
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
		queue.forEach(Recyclable::recycle);
		queue.clear();
	}

}
