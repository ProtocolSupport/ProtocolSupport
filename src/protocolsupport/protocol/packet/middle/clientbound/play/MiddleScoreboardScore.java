package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleScoreboardScore extends ClientBoundMiddlePacket {

	protected String name;
	protected int mode;
	protected String objectiveName;
	protected int value;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		name = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 40);
		mode = serverdata.readUnsignedByte();
		objectiveName = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
		if (mode != 1) {
			value = VarNumberSerializer.readVarInt(serverdata);
		}
	}

}
