package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleScoreboardTeam extends ClientBoundMiddlePacket {

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
		name = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
		mode = serverdata.readUnsignedByte();
		if ((mode == 0) || (mode == 2)) {
			displayName = ChatAPI.fromJSON(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
			friendlyFire = serverdata.readUnsignedByte();
			nameTagVisibility = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32);
			collisionRule = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 32);
			color = VarNumberSerializer.readVarInt(serverdata);
			prefix = ChatAPI.fromJSON(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
			suffix = ChatAPI.fromJSON(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
		}
		if ((mode == 0) || (mode == 3) || (mode == 4)) {
			players = ArraySerializer.readVarIntStringArray(serverdata, ProtocolVersionsHelper.LATEST_PC, 40);
		}
	}

}
