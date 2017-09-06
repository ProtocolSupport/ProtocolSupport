package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class ModalResponse extends ServerBoundMiddlePacket {

	protected int modalId;
	protected String modalJSON;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		modalId = VarNumberSerializer.readVarInt(clientdata);
		modalJSON = StringSerializer.readString(clientdata, connection.getVersion());
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}

}
