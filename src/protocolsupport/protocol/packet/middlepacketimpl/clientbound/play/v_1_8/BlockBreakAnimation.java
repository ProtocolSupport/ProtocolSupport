package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation<RecyclableCollection<PacketData>>  {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, version);
		serializer.writeVarInt(entityId);
		serializer.writePosition(position);
		serializer.writeByte(stage);
		return RecyclableSingletonList.create(serializer);
	}

}
