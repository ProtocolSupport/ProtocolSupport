package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleScoreboardTeam extends ClientBoundMiddlePacket {

	public MiddleScoreboardTeam(ConnectionImpl connection) {
		super(connection);
	}

	//TODO: mode enum
	protected String name;
	protected int mode;
	protected BaseComponent displayName;
	protected BaseComponent prefix;
	protected BaseComponent suffix;
	protected int friendlyFire;
	protected String nameTagVisibility;
	protected String collisionRule;
	protected int color;
	protected String[] players;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		name = StringSerializer.readVarIntUTF8String(serverdata);
		mode = serverdata.readUnsignedByte();
		if ((mode == 0) || (mode == 2)) {
			displayName = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
			friendlyFire = serverdata.readUnsignedByte();
			nameTagVisibility = StringSerializer.readVarIntUTF8String(serverdata);
			collisionRule = StringSerializer.readVarIntUTF8String(serverdata);
			color = VarNumberSerializer.readVarInt(serverdata);
			prefix = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
			suffix = ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(serverdata));
		}
		if ((mode == 0) || (mode == 3) || (mode == 4)) {
			players = ArraySerializer.readVarIntVarIntUTF8StringArray(serverdata);
		}
	}

}
