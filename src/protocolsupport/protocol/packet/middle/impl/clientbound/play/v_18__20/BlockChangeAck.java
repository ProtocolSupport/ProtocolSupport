package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_18__20;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockChangeAck;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class BlockChangeAck extends MiddleBlockChangeAck implements
IClientboundMiddlePacketV18,
IClientboundMiddlePacketV20 {

	public BlockChangeAck(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData blockchangeack = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_CHANGE_ACK);
		VarNumberCodec.writeVarInt(blockchangeack, sequence);
		io.writeClientbound(blockchangeack);
	}

}
