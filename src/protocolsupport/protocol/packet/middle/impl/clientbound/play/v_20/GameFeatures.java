package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleGameFeatures;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class GameFeatures extends MiddleGameFeatures implements
IClientboundMiddlePacketV20 {

	public GameFeatures(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData gamefeaturesPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_GAME_FEATURES);
		ArrayCodec.writeVarIntVarIntUTF8StringArray(gamefeaturesPacket, flags);
		io.writeClientbound(gamefeaturesPacket);
	}

}
