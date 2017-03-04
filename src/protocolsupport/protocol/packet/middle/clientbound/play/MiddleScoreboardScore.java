package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleScoreboardScore extends ClientBoundMiddlePacket {

	protected String name;
	protected int mode;
	protected String objectiveName;
	protected int value;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		name = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 40);
		mode = serverdata.readUnsignedByte();
		objectiveName = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 16);
		if (mode != 1) {
			value = VarNumberSerializer.readVarInt(serverdata);
		}
	}

}
