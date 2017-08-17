package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_OPEN, connection.getVersion());
		serializer.writeByte(windowId);
		serializer.writeByte(IdRemapper.WINDOWTYPE.getTable(connection.getVersion()).getRemap(type.toLegacyId()));
		PositionSerializer.writePEPosition(serializer, cache.getClickedPosition());
		if (type == WindowType.HORSE) { //TODO: check if this is correct.
			VarNumberSerializer.writeSVarLong(serializer, horseId);
		} else {
			VarNumberSerializer.writeSVarLong(serializer, -1);
		}
		System.out.println("Inventory open.. windowId: " + windowId + " type: "  + IdRemapper.WINDOWTYPE.getTable(connection.getVersion()).getRemap(type.toLegacyId()) + " position: " + cache.getClickedPosition() + " horseId: " + horseId);
		return RecyclableSingletonList.create(serializer);
	}

}
