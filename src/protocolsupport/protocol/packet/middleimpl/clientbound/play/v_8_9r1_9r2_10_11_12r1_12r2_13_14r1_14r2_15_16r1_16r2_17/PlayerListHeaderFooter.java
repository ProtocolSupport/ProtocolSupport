package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListHeaderFooter;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class PlayerListHeaderFooter extends MiddlePlayerListHeaderFooter {

	public PlayerListHeaderFooter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData playerlistheaderfooter = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_LIST_HEADER_FOOTER);
		StringCodec.writeVarIntUTF8String(playerlistheaderfooter, headerJson);
		StringCodec.writeVarIntUTF8String(playerlistheaderfooter, footerJson);
		codec.writeClientbound(playerlistheaderfooter);
	}

}
