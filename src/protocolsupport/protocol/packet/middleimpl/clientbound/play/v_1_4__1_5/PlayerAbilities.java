package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAbilities extends MiddlePlayerAbilities<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ABILITIES_ID, version);
		serializer.writeByte(flags);
		serializer.writeByte((int) (flyspeed * 255.0F));
		serializer.writeByte((int) (walkspeed * 255.0F));
		return RecyclableSingletonList.create(serializer);
	}

}
