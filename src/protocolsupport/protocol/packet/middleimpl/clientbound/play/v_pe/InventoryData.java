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
		switch(cache.getOpenedWindow()) { //A switch, we might want to transform some things in the future and not just simple remap.
			case FURNACE: {
				switch(type) {
					case 0: { //Fire icon (Burned ticks)
						return RecyclableSingletonList.create(create(version, windowId, 1, value));
					}
					case 2: { //Cook time (Maximum fuel time)
						return RecyclableSingletonList.create(create(version, windowId, 0, value));
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
