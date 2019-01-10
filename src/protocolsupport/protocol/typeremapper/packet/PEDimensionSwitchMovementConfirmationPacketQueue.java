package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayList;
import java.util.Collection;

import io.netty.buffer.ByteBuf;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PEDimensionSwitchMovementConfirmationPacketQueue {

	protected final ArrayList<ClientBoundPacketData> queue = new ArrayList<>(128);
	protected boolean isLocked = false;
	protected ConnectionImpl connection;

	public PEDimensionSwitchMovementConfirmationPacketQueue(ConnectionImpl connection) {
		this.connection = connection;
	}

	public RecyclableCollection<ClientBoundPacketData> processClientBoundPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();
			if (isPacketSendingAllowed() && !queue.isEmpty()) {
				ArrayList<ClientBoundPacketData> qclone = new ArrayList<ClientBoundPacketData>(queue);
				queue.clear();
				queue.trimToSize();
				processClientBoundPackets0(qclone, allowed);
			}
			processClientBoundPackets0(packets, allowed);
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}

	private void processClientBoundPackets0(Collection<ClientBoundPacketData> sendpackets, RecyclableArrayList<ClientBoundPacketData> allowed) {
		for (ClientBoundPacketData sendpacket : sendpackets) {
			if (sendpacket.getPacketId() == PEPacketIDs.CUSTOM_EVENT) {
				ByteBuf peak = sendpacket.duplicate();
				String tag = StringSerializer.readString(peak, ProtocolVersionsHelper.LATEST_PE);
				if (tag.equals(InternalPluginMessageRequest.PELockChannel)) {
					isLocked = true;
					continue;
				} else if (tag.equals(InternalPluginMessageRequest.PEUnlockChannel) && isLocked) {
					unlockQueue();
					continue;
				}
			}
			if (!isPacketSendingAllowed()) {
				queue.add(sendpacket);
			} else {
				allowed.add(sendpacket);
			}
		}
	}

	private boolean isPacketSendingAllowed() {
		return !isLocked;
	}

	private void unlockQueue() {
		MovementCache mcache = connection.getCache().getMovementCache();
		if (mcache.getHeldSpawn() != null) {
			ClientBoundMiddlePacket spawn = mcache.getHeldSpawn();
			ArrayList<ClientBoundPacketData> oldQueue = new ArrayList<>(queue);
			mcache.setHeldSpawn(null);
			queue.clear();
			queue.addAll(spawn.toData());
			queue.addAll(oldQueue);
		}
		isLocked = false;
	}

	public RecyclableCollection<ServerBoundPacketData> processServerBoundPackets(RecyclableCollection<ServerBoundPacketData> packets) {
		try {
			RecyclableArrayList<ServerBoundPacketData> allowed = RecyclableArrayList.create();
			boolean wasLocked = isLocked;
			for (ServerBoundPacketData packet : packets) {
				if (!isPacketSendingAllowed() && packet.getPacketType() == ServerBoundPacket.PLAY_CUSTOM_PAYLOAD) {
					ByteBuf peak = packet.duplicate();
					//This may also be mimicked by bungee during server changes.
					if (StringSerializer.readString(peak, ProtocolVersionsHelper.LATEST_PC).equals(InternalPluginMessageRequest.PEUnlockChannel)) {
						unlockQueue();
					}
				}
				allowed.add(packet);
			}
			if (wasLocked && !isLocked) {
				//Enable player mobility again
				connection.getCache().getMovementCache().setClientImmobile(false);
				queue.add(EntityMetadata.updatePlayerMobility(connection));
			}
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}
}
