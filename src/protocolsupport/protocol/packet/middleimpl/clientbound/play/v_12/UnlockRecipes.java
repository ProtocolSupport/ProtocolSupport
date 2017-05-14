package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnlockRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UnlockRecipes extends MiddleUnlockRecipes {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UNLOCK_RECIPES, version);
		serializer.writeShort(action);
		serializer.writeBoolean(openBook);
		serializer.writeBoolean(enableFiltering);
		ArraySerializer.writeVarIntIntArray(serializer, recipes1);
		ArraySerializer.writeVarIntIntArray(serializer, recipes2);
		return RecyclableSingletonList.create(serializer);
	}

}
