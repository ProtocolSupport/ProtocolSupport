package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerMessageDelete;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class PlayerMessageDelete extends MiddlePlayerMessageDelete implements
IClientboundMiddlePacketV20 {

	public PlayerMessageDelete(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData messagedeletePacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE_DELETE);
		ArrayCodec.writeVarIntByteArray(messagedeletePacket, signature);
		io.writeClientbound(messagedeletePacket);
	}

}
