package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CommandRequest extends ServerBoundMiddlePacket {

	public CommandRequest(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int ORIGIN_DEV_CONSOLE = 3;
	protected static final int ORIGIN_TEST = 4;

	protected String command;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		command = StringSerializer.readString(clientdata, connection.getVersion());
		int type = VarNumberSerializer.readVarInt(clientdata);
		MiscSerializer.readUUID(clientdata); // UUID
		StringSerializer.readString(clientdata, connection.getVersion()); // request ID
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
