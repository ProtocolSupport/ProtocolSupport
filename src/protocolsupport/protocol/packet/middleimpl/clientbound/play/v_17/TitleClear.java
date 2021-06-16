package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleClear;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class TitleClear extends MiddleTitleClear {

	public TitleClear(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titleclearPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TITLE_CLEAR);
		codec.writeClientbound(titleclearPacket);
	}

}
