package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListHeaderFooter;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class PlayerListHeaderFooter extends MiddlePlayerListHeaderFooter {

	public PlayerListHeaderFooter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData playerlistheaderfooter = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_PLAYER_LIST_HEADER_FOOTER);
		StringSerializer.writeVarIntUTF8String(playerlistheaderfooter, headerJson);
		StringSerializer.writeVarIntUTF8String(playerlistheaderfooter, footerJson);
		codec.writeClientbound(playerlistheaderfooter);
	}

}
