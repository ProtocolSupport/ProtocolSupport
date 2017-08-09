package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockAction extends MiddleBlockAction {
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		switch(this.type) {
			case 54: // chest
			case 130: // enderchest
			case 146: // trapped chest
				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.TILE_EVENT, connection.getVersion());
				PositionSerializer.writePEPosition(serializer, this.position);
				VarNumberSerializer.writeSVarInt(serializer, 1);
				if (this.info2 > 0) { // Open
					VarNumberSerializer.writeSVarInt(serializer, 2);
				} else { // Closed
					VarNumberSerializer.writeSVarInt(serializer, 0);
				}
				return RecyclableSingletonList.create(serializer);
			default:
				return RecyclableEmptyList.get();
		}
	}
}
