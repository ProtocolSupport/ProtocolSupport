package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUpdateViewDistance;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UpdateViewDistance extends MiddleUpdateViewDistance {

	public UpdateViewDistance(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData updateviewdistance = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_UPDATE_VIEW_DISTANCE);
		VarNumberSerializer.writeVarInt(updateviewdistance, distance);
		codec.write(updateviewdistance);
	}

}
