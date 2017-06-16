package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleScoreboardObjective extends ClientBoundMiddlePacket {

	protected String name;
	protected int mode;
	protected String value;
	protected String type;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		name = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
		mode = serverdata.readUnsignedByte();
		if (mode != 1) {
			value = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32);
			type = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32);
		}
	}

}
