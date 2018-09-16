package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class BlockAction extends MiddleBlockAction {

	public BlockAction(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableEmptyList.get();
// TODO Fix this shit
//		switch (this.type) {
//			case 54: // chest
//			case 130: // enderchest
//			case 146: // trapped chest
//				ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.TILE_EVENT);
//				PositionSerializer.writePEPosition(serializer, this.position);
//				VarNumberSerializer.writeSVarInt(serializer, 1);
//				if (this.info2 > 0) { // Open
//					VarNumberSerializer.writeSVarInt(serializer, 2);
//				} else { // Closed
//					VarNumberSerializer.writeSVarInt(serializer, 0);
//				}
//				return RecyclableSingletonList.create(serializer);
//			default: {
//				return RecyclableEmptyList.get();
//			}
//		}
	}

}
