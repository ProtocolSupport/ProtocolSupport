package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetViewCenter;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SetViewCenter extends MiddleSetViewCenter {

	public SetViewCenter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData setviewcenter = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SET_VIEW_CENTER);
		PositionCodec.writeVarIntChunkCoord(setviewcenter, chunk);
		codec.writeClientbound(setviewcenter);
	}

}
