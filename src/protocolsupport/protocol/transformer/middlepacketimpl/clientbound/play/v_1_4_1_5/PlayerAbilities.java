package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAbilities extends MiddlePlayerAbilities<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ABILITIES_ID, version);
		serializer.writeByte(flags);
		serializer.writeByte((int) (flyspeed * 255.0F));
		serializer.writeByte((int) (walkspeed * 255.0F));
		return RecyclableSingletonList.create(serializer);
	}

}
