package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class DeclareRecipes extends MiddleDeclareRecipes {

	public DeclareRecipes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		//TODO: implement
		return RecyclableEmptyList.get();
	}

}
