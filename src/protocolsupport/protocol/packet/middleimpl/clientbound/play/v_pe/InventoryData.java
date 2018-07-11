package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryData;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryData extends MiddleInventoryData {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		switch(cache.getWindowCache().getOpenedWindow()) {
			case FURNACE: {
				switch(type) {
					case 0: { //Fire icon (Update how much fuel has burned)
						int peValue = Math.round((((float) value * 200) / invCache.getFuelTime()));
						return RecyclableSingletonList.create(create(windowId, 1, peValue));
					}
					case 1: { //Fuel burn time (Set max amount of fuel available)
						if(value != 0) {
							invCache.setFuelTime(value);
						}
						break;
					}
					case 2: { //Cook time (Update how long the item has been cooking)
						int peValue = Math.round((((float) value * 200) / invCache.getSmeltTime()));
						return RecyclableSingletonList.create(create(windowId, 0, peValue));
					}
					case 3: { //Smelt time (Set how long it takes for the item to smelt)
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
					invCache.getFakeEnchanting().updateOptionXP(type, value);
				} else if (type == 3) {
					break; //Use for weird item names?
				} else if (type <= 6) { //(4-6) EnchantmentId per option
					invCache.getFakeEnchanting().updateOptionEnch(type - 4, value);
				} else if (type <= 9) { //(7-9) EnchantmentLvl per option
					invCache.getFakeEnchanting().updateOptionLevel(type - 7, value);
				}
				break;
			}
			case BEACON: {
				switch(type) {
					case 0: {
						invCache.getFakeBeacon().setLevel(value);
						return invCache.getFakeBeacon().updateTemple(version, cache);
					}
					case 1: {
						invCache.getFakeBeacon().setPrimaryEffect(value);
						return invCache.getFakeBeacon().updateNBT(version, cache);
					}
					case 2: {
						invCache.getFakeBeacon().setSecondaryEffect(value);
						return invCache.getFakeBeacon().updateNBT(version, cache);
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
						return RecyclableSingletonList.create(create(windowId, 0, Math.round(value / 2) * 2));
					}
					case 1: { //Fuel time (0 - 20) (20 is full)
						//TODO: make a formula?
						//To be honest I have no clue how it works on the inside, I just tried until the bar matches PC :F
						RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
						packets.add(create( windowId, 1, value > 0 ? 20 : 0));
						int inv = 20 - value;
						if (inv > 6)  { inv += (inv- 6); }
						if (inv > 10) { inv += (inv-10); }
						if (inv > 30) { inv += (inv-30); }
						if (inv > 50) { inv += (inv-50); }
						if (inv > 60) { inv += (inv-60); }
						packets.add(create( windowId, 2, 20 + inv));
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
	
	public static ClientBoundPacketData create(int windowId, int type, int value) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_DATA);
		serializer.writeByte(windowId);
		VarNumberSerializer.writeSVarInt(serializer, type);
		VarNumberSerializer.writeSVarInt(serializer, value);
		return serializer;
	}
	
}
