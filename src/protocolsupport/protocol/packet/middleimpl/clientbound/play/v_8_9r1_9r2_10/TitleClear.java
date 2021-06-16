package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleClear;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class TitleClear extends MiddleTitleClear {

	public TitleClear(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titleclearPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_CLEAR);
		VarNumberSerializer.writeVarInt(titleclearPacket, reset ? 4 : 3); //legacy title action (3 - hide title, 4 - reset title)
		codec.writeClientbound(titleclearPacket);
	}

}
