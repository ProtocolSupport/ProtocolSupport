package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleScoreboardScore extends ClientBoundMiddlePacket {

	protected MiddleScoreboardScore(MiddlePacketInit init) {
		super(init);
	}

	protected String name;
	protected int mode;
	protected String objectiveName;
	protected int value;

	@Override
	protected void decode(ByteBuf serverdata) {
		name = StringCodec.readVarIntUTF8String(serverdata);
		mode = serverdata.readUnsignedByte();
		objectiveName = StringCodec.readVarIntUTF8String(serverdata);
		if (mode != 1) {
			value = VarNumberCodec.readVarInt(serverdata);
		}
	}

}
