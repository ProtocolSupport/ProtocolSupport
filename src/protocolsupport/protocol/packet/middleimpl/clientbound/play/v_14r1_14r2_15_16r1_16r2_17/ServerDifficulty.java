package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleServerDifficulty;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class ServerDifficulty extends MiddleServerDifficulty {

	public ServerDifficulty(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serverdifficulty = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SERVER_DIFFICULTY);
		MiscDataCodec.writeByteEnum(serverdifficulty, difficulty);
		serverdifficulty.writeBoolean(locked);
		codec.writeClientbound(serverdifficulty);
	}

}
