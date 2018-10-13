package protocolsupport.protocol.packet.middle.serverbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class MiddleUpdateStructureBlock extends ServerBoundMiddlePacket {

	public MiddleUpdateStructureBlock(ConnectionImpl connection) {
		super(connection);
	}

	//TODO: structure (who cares about structure block anyway???)
	protected ByteBuf data;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		data = MiscSerializer.readAllBytesSlice(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}

}
