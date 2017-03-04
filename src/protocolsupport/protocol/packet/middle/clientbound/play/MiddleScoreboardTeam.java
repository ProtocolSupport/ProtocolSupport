package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

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
		name = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 16);
		mode = serverdata.readUnsignedByte();
		if ((mode == 0) || (mode == 2)) {
			displayName = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 32);
			prefix = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 16);
			suffix = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 16);
			friendlyFire = serverdata.readUnsignedByte();
			nameTagVisibility = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 32);
			collisionRule = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 32);
			color = serverdata.readUnsignedByte();
		}
		if ((mode == 0) || (mode == 3) || (mode == 4)) {
			players = new String[VarNumberSerializer.readVarInt(serverdata)];
			for (int i = 0; i < players.length; i++) {
				players[i] = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 40);
			}
		}
	}

}
