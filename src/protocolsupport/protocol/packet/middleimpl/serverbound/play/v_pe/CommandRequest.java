package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CommandRequest extends ServerBoundMiddlePacket {
	protected String command;
	//private static final int ORIGIN_PLAYER = 0;
	//private static final int ORIGIN_BLOCK = 1;
	//private static final int ORIGIN_MINECART_BLOCK = 2;
	private static final int ORIGIN_DEV_CONSOLE = 3;
	private static final int ORIGIN_TEST = 4;
	//private static final int ORIGIN_AUTOMATION_PLAYER = 5;
	//private static final int ORIGIN_CLIENT_AUTOMATION = 6;
	//private static final int ORIGIN_DEDICATED_SERVER = 7;
	//private static final int ORIGIN_ENTITY = 8;
	//private static final int ORIGIN_VIRTUAL = 9;
	//private static final int ORIGIN_GAME_ARGUMENT = 10;
	//private static final int ORIGIN_ENTITY_SERVER = 11;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		command = StringSerializer.readString(clientdata, connection.getVersion());
		// Command Origin Data
		int type = VarNumberSerializer.readVarInt(clientdata); // type
		MiscSerializer.readUUID(clientdata); // UUID
		StringSerializer.readString(clientdata, ProtocolVersion.MINECRAFT_PE); // request ID
		if ((type == ORIGIN_DEV_CONSOLE) || (type == ORIGIN_TEST)) {
			VarNumberSerializer.readSVarLong(clientdata); // ???
		}
		clientdata.readBoolean(); // isInternal
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(MiddleChat.create(command));
	}
}
