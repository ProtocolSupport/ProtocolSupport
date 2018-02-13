package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class MapInfoRequest extends ServerBoundMiddlePacket {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		VarNumberSerializer.readSVarLong(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}
}
