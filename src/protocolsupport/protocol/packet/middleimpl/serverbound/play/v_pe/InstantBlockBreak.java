package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class InstantBlockBreak extends ServerBoundMiddlePacket {

	protected Position position;

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		position = PositionSerializer.readPEPosition(clientdata);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.START_DIG, position, 0));
		packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_DIG, position, 0));
		return packets;
	}

}
