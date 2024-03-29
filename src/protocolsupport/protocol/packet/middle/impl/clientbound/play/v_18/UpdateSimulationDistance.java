package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_18;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleUpdateSimulationDistance;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;

public class UpdateSimulationDistance extends MiddleUpdateSimulationDistance implements IClientboundMiddlePacketV18 {

	public UpdateSimulationDistance(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData simulationdistancePacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_SIMULATION_DISTANCE);
		VarNumberCodec.writeVarInt(simulationdistancePacket, distance);
		io.writeClientbound(simulationdistancePacket);
	}

}
