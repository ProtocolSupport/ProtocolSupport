package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_OPEN, connection.getVersion());
		serializer.writeByte(windowId);
		serializer.writeByte(type.toLegacyId());
		PositionSerializer.writePEPosition(serializer, new protocolsupport.protocol.utils.types.Position(0, 0, 0));
		if (type == WindowType.HORSE) { //TODO: check if this is correct.
			VarNumberSerializer.writeVarInt(serializer, horseId);
		} else {
			VarNumberSerializer.writeVarInt(serializer, -1);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
