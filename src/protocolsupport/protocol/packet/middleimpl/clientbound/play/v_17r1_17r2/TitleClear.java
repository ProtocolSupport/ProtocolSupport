package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

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
		titleclearPacket.writeBoolean(reset);
		codec.writeClientbound(titleclearPacket);
	}

}
