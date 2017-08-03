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
	protected int type;
	protected String requestId;
	protected int playerId;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		command = StringSerializer.readString(clientdata, connection.getVersion());
		type = VarNumberSerializer.readVarInt(clientdata);
		requestId = StringSerializer.readString(clientdata, connection.getVersion());
		playerId = VarNumberSerializer.readSVarInt(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(MiddleChat.create(command));
	}
}
