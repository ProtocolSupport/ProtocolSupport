package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnlockRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UnlockRecipes extends MiddleUnlockRecipes {

	public UnlockRecipes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_UNLOCK_RECIPES);
		MiscSerializer.writeVarIntEnum(serializer, action);
		serializer.writeBoolean(craftRecipeBookOpen);
		serializer.writeBoolean(craftRecipeBookFiltering);
		serializer.writeBoolean(smeltingRecipeBookOpen);
		serializer.writeBoolean(smeltingRecipeBookFiltering);
		ArraySerializer.writeVarIntVarIntUTF8StringArray(serializer, recipes1);
		if (action == Action.INIT) {
			ArraySerializer.writeVarIntVarIntUTF8StringArray(serializer, recipes2);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
