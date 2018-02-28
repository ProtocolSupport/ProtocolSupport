package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryData;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.pe.PEInventoryCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryData extends MiddleInventoryData {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		PEInventoryCache invCache = cache.getPEDataCache().getInventoryCache();
		switch(cache.getOpenedWindow()) {
			case FURNACE: {
				switch(type) {
					case 0: { //Fire icon (Burned ticks) (Tick in PE is 50ms while in PC it's 20)
						int peValue = Math.round((((float) value * 400) / invCache.getFuelTime()));
						return RecyclableSingletonList.create(create(version, windowId, 2, peValue));
					}
					case 1: { //Fuel burn time
						if(value != 0) {
							invCache.setFuelTime(value);
						}
						break;
					}
					case 2: { //Cook time (How long the item has been cooking)
						return RecyclableSingletonList.create(create(version, windowId, 0, Math.round((((float) value * 400) / invCache.getSmeltTime()))));
					}
					case 3: { //Smelt time (How long it takes for the item to smelt)
						if(value != 0) {
							invCache.setSmeltTime(value);
						}
						break;
					}
				}
				break;
			}
			case ENCHANT: {
				if (type <= 2) { //(0-2) EnchantmentXP per option
					invCache.getEnchantHopper().updateOptionXP(type, value);
				} else if (type == 3) {
					break; //Use for weird item names?
				} else if (type <= 6) { //(4-6) EnchantmentId per option
					invCache.getEnchantHopper().updateOptionEnch(type - 4, value);
				} else if (type <= 9) { //(7-9) EnchantmentLvl per option
					invCache.getEnchantHopper().updateOptionLevel(type - 7, value);
				}
				break;
			}
			case BEACON: {
				switch(type) {
					case 0: {
						break;
					}
					case 1: {
						invCache.getBeaconTemple().setPrimaryEffect(value);
						return invCache.getBeaconTemple().updateNBT(version, cache);
					}
					case 2: {
						invCache.getBeaconTemple().setSecondaryEffect(value);
						return invCache.getBeaconTemple().updateNBT(version, cache);
					}
				}
				break;
			}
			case ANVIL: {
				break; //Once PE starts trusting the server we need to send xp cost from here.
			}
			case BREWING: {
				switch(type) {
					case 0: { //Brew time (0 - 400) (400 is empty)
						return RecyclableSingletonList.create(create(version, windowId, 0, Math.round(value / 2) * 2));
					}
					case 1: { //Fuel time (0 - 20) (20 is full)
						//TODO: make a formula?
						//To be honest I have no clue how it works on the inside, I just tried until the bar matches PC :F
						RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
						packets.add(create(version, windowId, 1, value > 0 ? 20 : 0));
						int inv = 20 - value;
						if (inv > 6)  { inv += (inv- 6); }
						if (inv > 10) { inv += (inv-10); }
						if (inv > 30) { inv += (inv-30); }
						if (inv > 50) { inv += (inv-50); }
						if (inv > 60) { inv += (inv-60); }
						packets.add(create(version, windowId, 2, 20 + inv));
						return packets;
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
		serializer.writeByte(windowId);
		VarNumberSerializer.writeSVarInt(serializer, type);
		VarNumberSerializer.writeSVarInt(serializer, value);
		return serializer;
	}
	
}
