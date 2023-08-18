package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerListRemoveEntry;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class PlayerListRemoveEntry extends MiddlePlayerListRemoveEntry implements
IClientboundMiddlePacketV20 {

	public PlayerListRemoveEntry(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData removeentryPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_LIST_ENTRY_REMOVE);
		ArrayCodec.writeVarIntTArray(removeentryPacket, entries.keySet(), UUIDCodec::writeUUID);
		io.writeClientbound(removeentryPacket);
	}

}
