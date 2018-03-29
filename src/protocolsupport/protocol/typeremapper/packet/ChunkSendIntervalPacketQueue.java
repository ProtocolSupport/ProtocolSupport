package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ChunkSendIntervalPacketQueue {

	protected static final IntOpenHashSet queuedPacketTypes = new IntOpenHashSet(new int[] {
		ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, ClientBoundPacket.PLAY_CHUNK_UNLOAD_ID,
		ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID,
		ClientBoundPacket.PLAY_BLOCK_ACTION_ID, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID,
		ClientBoundPacket.PLAY_UPDATE_TILE_ID, ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID,
		ClientBoundPacket.PLAY_BED_ID
	});
	protected static final long chunkSendInterval = TimeUnit.MILLISECONDS.toNanos(Utils.getJavaPropertyValue("chunksend18interval", 5L, Long::parseLong));

	protected State state = State.ALLOWED;
	protected final ArrayList<ClientBoundPacketData> queue = new ArrayList<>(1024);

	public RecyclableCollection<ClientBoundPacketData> processPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();
			if (!queue.isEmpty()) {
				@SuppressWarnings("unchecked")
				ArrayList<ClientBoundPacketData> qclone = (ArrayList<ClientBoundPacketData>) queue.clone();
				queue.clear();
				processPackets0(qclone, allowed);
			}
			processPackets0(packets, allowed);
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}

	protected void processPackets0(Collection<ClientBoundPacketData> packets, RecyclableCollection<ClientBoundPacketData> allowed) {
		for (ClientBoundPacketData packet : packets) {
			if (state != State.ALLOWED) {
				if (queuedPacketTypes.contains(packet.getPacketId())) {
					queue.add(packet);
				} else {
					allowed.add(packet);
				}
				continue;
			}
			if (packet.getPacketId() == ClientBoundPacket.PLAY_CHUNK_SINGLE_ID) {
				state = State.WAITING_UNLOCK_SCHEDULE;
			}
			allowed.add(packet);
		}
	}

	public void unlock() {
		state = State.ALLOWED;
	}

	public long getUnlockDelay() {
		if (state != State.WAITING_UNLOCK_SCHEDULE) {
			return -1;
		}
		state = State.WAITING_UNLOCK;
		return chunkSendInterval;
	}

	protected static enum State {
		ALLOWED, WAITING_UNLOCK_SCHEDULE, WAITING_UNLOCK;
	}

}
