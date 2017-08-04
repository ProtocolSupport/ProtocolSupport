package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CommandRequest extends ServerBoundMiddlePacket {

	protected String command;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		command = StringSerializer.readString(clientdata, connection.getVersion());
		VarNumberSerializer.readVarInt(clientdata); // type
		StringSerializer.readString(clientdata, connection.getVersion()); // request ID
		VarNumberSerializer.readSVarInt(clientdata); // player ID
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(MiddleChat.create(command));
	}
}
