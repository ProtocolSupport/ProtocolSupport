package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class DeclareRecipes extends MiddleDeclareRecipes {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_DECLARE_RECIPES);
		serializer.writeBytes(data);
		return RecyclableSingletonList.create(serializer);
	}

}
