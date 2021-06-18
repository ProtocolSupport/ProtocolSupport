package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleClear;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class TitleClear extends MiddleTitleClear {

	public TitleClear(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titleclearPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_CLEAR);
		VarNumberCodec.writeVarInt(titleclearPacket, reset ? 5 : 4); //legacy title action (4 - hide title, 5 - reset title)
		codec.writeClientbound(titleclearPacket);
	}

}
