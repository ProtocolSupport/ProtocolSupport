package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleScoreboardScore extends ClientBoundMiddlePacket {

	public MiddleScoreboardScore(ConnectionImpl connection) {
		super(connection);
	}

	protected String name;
	protected int mode;
	protected String objectiveName;
	protected int value;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		name = StringSerializer.readVarIntUTF8String(serverdata);
		mode = serverdata.readUnsignedByte();
		objectiveName = StringSerializer.readVarIntUTF8String(serverdata);
		if (mode != 1) {
			value = VarNumberSerializer.readVarInt(serverdata);
		}
	}

}
