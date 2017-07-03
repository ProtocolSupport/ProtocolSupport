package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleScoreboardTeam extends ClientBoundMiddlePacket {

	protected String name;
	protected int mode;
	protected String displayName;
	protected String prefix;
	protected String suffix;
	protected int friendlyFire;
	protected String nameTagVisibility;
	protected String collisionRule;
	protected int color;
	protected String[] players;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		name = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
		mode = serverdata.readUnsignedByte();
		if ((mode == 0) || (mode == 2)) {
			displayName = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32);
			prefix = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
			suffix = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
			friendlyFire = serverdata.readUnsignedByte();
			nameTagVisibility = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32);
			collisionRule = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32);
			color = serverdata.readUnsignedByte();
		}
		if ((mode == 0) || (mode == 3) || (mode == 4)) {
			players = ArraySerializer.readVarIntStringArray(serverdata, ProtocolVersionsHelper.LATEST_PC, 40);
		}
	}

}
