package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderWarnDelay;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class WorldBorderWarnDelay extends MiddleWorldBorderWarnDelay {

	public WorldBorderWarnDelay(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_WARN_DELAY);
		VarNumberCodec.writeVarInt(worldborderPacket, warnDelay);
		codec.writeClientbound(worldborderPacket);
	}

}
