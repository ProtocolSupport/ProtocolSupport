package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateViewDistance;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class UpdateViewDistance extends MiddleUpdateViewDistance {

	public UpdateViewDistance(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData updateviewdistance = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_UPDATE_VIEW_DISTANCE);
		VarNumberCodec.writeVarInt(updateviewdistance, distance);
		codec.writeClientbound(updateviewdistance);
	}

}
