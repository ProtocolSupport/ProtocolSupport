package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleClear;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class TitleClear extends MiddleTitleClear {

	public TitleClear(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titleclearPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TITLE_CLEAR);
		VarNumberSerializer.writeVarInt(titleclearPacket, reset ? 5 : 4); //legacy title action (4 - hide title, 5 - reset title)
		codec.writeClientbound(titleclearPacket);
	}

}
