package protocolsupport.protocol.packet.middle.serverbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class MiddleUpdateStructureBlock extends ServerBoundMiddlePacket {

	//TODO: structure (who cares about structure block anyway???)
	protected byte[] data;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		data = MiscSerializer.readAllBytes(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}

}
