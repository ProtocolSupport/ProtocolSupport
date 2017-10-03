package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryData;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

//Values nicked from PMMP ;)
public class InventoryData extends MiddleInventoryData {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		switch(cache.getOpenedWindow()) {
			case FURNACE: {
				switch(type) {
					case 0: { //Fire icon (Burned ticks) (Tick in PE is 50ms while in PC it's PE)
						return RecyclableSingletonList.create(create(version, windowId, 2, (cache.getFurnaceFuelTime() == 0 ? 0 : (int) (((float) value * 400) / cache.getFurnaceFuelTime()))));
					}
					case 1: { //Fuel burn time
						cache.setFurnaceFuelTime(value);
						break;
					}
					case 2: { //Cook time (How long the item has been cooking)
						cache.getActionNumber();
						return RecyclableSingletonList.create(create(version, windowId, 0, (cache.getFurnaceSmeltTime() == 0 ? 0 : (int) (((float) value * 400) / cache.getFurnaceSmeltTime()))));
					}
					case 3: { //Smelt time (How long it takes for the item to smelt)
						cache.setFurnaceSmeltTime(value);
						break;
					}
				}
				break;
			}
			default: {
				break;
			}
		}
		return RecyclableEmptyList.get();
	}

	public static ClientBoundPacketData create(ProtocolVersion version, int windowId, int type, int value) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_DATA, version);
		VarNumberSerializer.writeVarInt(serializer, windowId);
		VarNumberSerializer.writeVarInt(serializer, type);
		VarNumberSerializer.writeVarInt(serializer, value);
		return serializer;
	}
	
}
