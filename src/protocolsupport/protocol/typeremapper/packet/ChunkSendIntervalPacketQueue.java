package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.utils.recyclable.Recyclable;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ChunkSendIntervalPacketQueue {

	protected static final IntOpenHashSet queuedPacketTypes = new IntOpenHashSet(new int[] {
		ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, ClientBoundPacket.PLAY_CHUNK_UNLOAD_ID,
		ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID,
		ClientBoundPacket.PLAY_BLOCK_ACTION_ID, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID,
		ClientBoundPacket.PLAY_UPDATE_TILE_ID, ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID,
		ClientBoundPacket.LEGACY_PLAY_USE_BED_ID
	});
	protected static boolean shouldQueue(ClientBoundPacketData packet) {
		return queuedPacketTypes.contains(packet.getPacketId());
	}
	protected static boolean shouldLock(ClientBoundPacketData packet) {
		return packet.getPacketId() == ClientBoundPacket.PLAY_CHUNK_SINGLE_ID;
	}
	protected static final long chunkSendInterval = TimeUnit.MILLISECONDS.toNanos(JavaSystemProperty.getValue("chunksend18interval", 5L, Long::parseLong));

	protected State state = State.UNLOCKED;
	protected final ArrayDeque<ClientBoundPacketData> queue = new ArrayDeque<>(1024);
	public RecyclableCollection<ClientBoundPacketData> processPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();

			//if locked - just route (add to queue if needs to be queued or add to allowed otherwise) normal packets
			if (state != State.UNLOCKED) {
				processPacketsWhenLocked(packets.iterator(), allowed);
				return allowed;
			}

			//if not locked - first process queued packets
			if (!queue.isEmpty()) {
				//poll queued packet and add it to allowed packets, stop after hitting the lock packet
				//if the lock packet is hit also route normal packets
				ClientBoundPacketData qPacket = null;
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
			Iterator<ClientBoundPacketData> iterator = packets.iterator();
			while (iterator.hasNext()) {
				ClientBoundPacketData packet = iterator.next();
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

	protected void processPacketsWhenLocked(Iterator<ClientBoundPacketData> iterator, RecyclableCollection<ClientBoundPacketData> allowed) {
		while (iterator.hasNext()) {
			ClientBoundPacketData packet = iterator.next();
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
