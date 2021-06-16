package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSyncPing;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SyncPing extends MiddleSyncPing {

	public SyncPing(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData syncpingPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_SYNC_PING);
		syncpingPacket.writeInt(id);
		codec.writeClientbound(syncpingPacket);
	}

}
