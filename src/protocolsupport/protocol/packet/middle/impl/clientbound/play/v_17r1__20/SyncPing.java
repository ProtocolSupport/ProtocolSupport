package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__20;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSyncPing;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class SyncPing extends MiddleSyncPing implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18,
IClientboundMiddlePacketV20 {

	public SyncPing(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData syncpingPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SYNC_PING);
		syncpingPacket.writeInt(id);
		io.writeClientbound(syncpingPacket);
	}

}
