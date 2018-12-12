package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayList;
import java.util.Collection;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PEDimensionSwitchMovementConfirmationPacketQueue {

	protected final ArrayList<ClientBoundPacketData> queue = new ArrayList<>(2000);
	protected boolean isLocked = false;

	@SuppressWarnings("unchecked")
	public RecyclableCollection<ClientBoundPacketData> processClientBoundPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();
			if (isPacketSendingAllowed() && !queue.isEmpty()) {
				ArrayList<ClientBoundPacketData> qclone = (ArrayList<ClientBoundPacketData>) queue.clone();
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
			if (!isPacketSendingAllowed()) {
				queue.add(sendpacket);
			} else {
				allowed.add(sendpacket);
				if (sendpacket.getPacketId() == PEPacketIDs.CUSTOM_EVENT) {
					ByteBuf peak = sendpacket.duplicate();
					if (StringSerializer.readString(peak, ProtocolVersionsHelper.LATEST_PE).equals(InternalPluginMessageRequest.PELockChannel)) {
						isLocked = true;
					}
				}
			}
		}
	}

	private boolean isPacketSendingAllowed() {
		return !isLocked;
	}

	public RecyclableCollection<ServerBoundPacketData> processServerBoundPackets(RecyclableCollection<ServerBoundPacketData> packets, ConnectionImpl connection) {
		try {
			RecyclableArrayList<ServerBoundPacketData> allowed = RecyclableArrayList.create();
			boolean wasLocked = isLocked;
			for (ServerBoundPacketData packet : packets) {
				if (!isPacketSendingAllowed() && packet.getPacketType() == ServerBoundPacket.PLAY_CUSTOM_PAYLOAD) {
					ByteBuf peak = packet.duplicate();
					//This may also be mimicked by bungee during server changes.
					if (StringSerializer.readString(peak, ProtocolVersionsHelper.LATEST_PC).equals(InternalPluginMessageRequest.PEUnlockChannel)) {
						isLocked = false;
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
