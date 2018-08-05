package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class CraftRecipeRequest extends ServerBoundMiddlePacket {

	public CraftRecipeRequest(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readUnsignedByte();
		VarNumberSerializer.readVarInt(clientdata);
		clientdata.readBoolean();
	}

}
