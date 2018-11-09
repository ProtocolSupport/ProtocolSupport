package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayList;
import java.util.Collection;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PEDimensionSwitchMovementConfirmationPacketQueue {

	protected final ArrayList<ClientBoundPacketData> queue = new ArrayList<>(2000);
	protected State state = State.UNLOCKED;

	@SuppressWarnings("unchecked")
	public RecyclableCollection<ClientBoundPacketData> processClientBoundPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();
			if (isPacketSendingAllowed() && !queue.isEmpty()) {
				ArrayList<ClientBoundPacketData> qclone = (ArrayList<ClientBoundPacketData>) queue.clone();
				queue.clear();
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
					String tag = StringSerializer.readString(peak, ProtocolVersion.MINECRAFT_PE);
					if (tag.equals("ps:clientlock")) {
						state = State.LOCKED;
					}
				}
			}
		}
	}

	private boolean isPacketSendingAllowed() {
		return state == State.UNLOCKED;
	}

	public RecyclableCollection<ServerBoundPacketData> processServerBoundPackets(RecyclableCollection<ServerBoundPacketData> packets) {
		try {
			RecyclableArrayList<ServerBoundPacketData> allowed = RecyclableArrayList.create();
			for (ServerBoundPacketData packet : packets) {
				if (!isPacketSendingAllowed() && packet.getPacketType() == ServerBoundPacket.PLAY_CUSTOM_PAYLOAD) {
					ByteBuf peak = packet.duplicate();
					String tag = StringSerializer.readString(peak, ProtocolVersionsHelper.LATEST_PC);
					// this may also be mimicked by bungee during server changes
					if (tag.equals("ps:clientunlock")) {
						//TODO: do we need to do something to trigger the queue flush a bit faster?
						state = State.UNLOCKED;
					}
				} else if (!isPacketSendingAllowed() && packet.getPacketType() == ServerBoundPacket.PLAY_POSITION_LOOK) {
					// client is alive in its world
					state = State.UNLOCKED;
				}
				allowed.add(packet);
			}
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}

	protected enum State {
		UNLOCKED, LOCKED
	}

}
