package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.Position;

public abstract class MiddleUpdateSign extends ServerBoundMiddlePacket {

	protected MiddleUpdateSign(IMiddlePacketInit init) {
		super(init);
	}

	protected final Position position = new Position(0, 0, 0);
	protected String[] lines = new String[4];

	@Override
	protected void write() {
		io.writeServerbound(create(position, lines));
	}

	public static ServerBoundPacketData create(Position position, String[] lines) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_UPDATE_SIGN);
		PositionCodec.writePosition(creator, position);
		for (int i = 0; i < lines.length; i++) {
			StringCodec.writeVarIntUTF8String(creator, lines[i]);
		}
		return creator;
	}

}
