package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.utils.recyclable.Recyclable;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ChunkSendIntervalPacketQueue {

	protected static final IntOpenHashSet queuedPacketTypes = new IntOpenHashSet(new int[] {
		PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE.getId(), PacketType.CLIENTBOUND_PLAY_CHUNK_UNLOAD.getId(),
		PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE.getId(), PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI.getId(),
		PacketType.CLIENTBOUND_PLAY_BLOCK_ACTION.getId(), PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION.getId(),
		PacketType.CLIENTBOUND_PLAY_UPDATE_TILE.getId(), PacketType.CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN_ID.getId(),
		PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED_ID.getId()
	});
	protected static boolean shouldQueue(IPacketData packet) {
		return queuedPacketTypes.contains(packet.getPacketType().getId());
	}
	protected static boolean shouldLock(IPacketData packet) {
		return packet.getPacketType() == PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE;
	}
	protected static final long chunkSendInterval = TimeUnit.MILLISECONDS.toNanos(JavaSystemProperty.getValue("chunksend18interval", 5L, Long::parseLong));

	protected State state = State.UNLOCKED;
	protected final ArrayDeque<IPacketData> queue = new ArrayDeque<>(1024);
	public RecyclableCollection<? extends IPacketData> processPackets(RecyclableCollection<? extends IPacketData> packets) {
		try {
			RecyclableArrayList<IPacketData> allowed = RecyclableArrayList.create();

			//if locked - just route (add to queue if needs to be queued or add to allowed otherwise) normal packets
			if (state != State.UNLOCKED) {
				processPacketsWhenLocked(packets.iterator(), allowed);
				return allowed;
			}

			//if not locked - first process queued packets
			if (!queue.isEmpty()) {
				//poll queued packet and add it to allowed packets, stop after hitting the lock packet
				//if the lock packet is hit also route normal packets
				IPacketData qPacket = null;
				while ((qPacket = queue.pollFirst()) != null) {
					allowed.add(qPacket);
					if (shouldLock(qPacket)) {
						state = State.LOCKED;
						processPacketsWhenLocked(packets.iterator(), allowed);
						return allowed;
					}
				}
			}

			//now if still not locked - process normal packets
			//add all packets to allowed, stop after hitting lock packet
			Iterator<? extends IPacketData> iterator = packets.iterator();
			while (iterator.hasNext()) {
				IPacketData packet = iterator.next();
				allowed.add(packet);
				if (shouldLock(packet)) {
					state = State.LOCKED;
					break;
				}
			}
			//route the rest of the normal packets
			processPacketsWhenLocked(iterator, allowed);
			return allowed;

		} finally {
			packets.recycleObjectOnly();
		}
	}

	protected void processPacketsWhenLocked(Iterator<? extends IPacketData> iterator, RecyclableCollection<IPacketData> allowed) {
		while (iterator.hasNext()) {
			IPacketData packet = iterator.next();
			if (shouldQueue(packet)) {
				queue.addLast(packet);
			} else {
				allowed.add(packet);
			}
		}
	}

	public void unlock() {
		state = State.UNLOCKED;
	}

	public long getUnlockDelay() {
		if (state != State.LOCKED) {
			return -1;
		}
		state = State.WAITING_UNLOCK;
		return chunkSendInterval;
	}

	protected static enum State {
		UNLOCKED, LOCKED, WAITING_UNLOCK;
	}

	public void release() {
		queue.forEach(Recyclable::recycle);
		queue.clear();
	}

}
