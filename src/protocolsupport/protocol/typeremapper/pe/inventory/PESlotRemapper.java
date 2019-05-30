package protocolsupport.protocol.typeremapper.pe.inventory;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket.InvTransaction;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableArrayList;

public class PESlotRemapper {

	public static ClientBoundPacketData remapClientBoundSlot(NetworkDataCache cache, ProtocolVersion version, NetworkItemStack item, int slot) {
		String locale = cache.getAttributesCache().getLocale();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		WindowCache winCache = cache.getWindowCache();
		switch (cache.getWindowCache().getOpenedWindow()) {
			case PLAYER: {
				if (slot == 0) {
					return InventorySetSlot.create(version, locale, PESource.POCKET_CRAFTING_RESULT, 0, item);
				} else if (slot <= 4) {
					if (item.isNull()) {
						return InventorySetSlot.create(version, locale, PESource.POCKET_CRAFTING_GRID_REMOVE, slot - 1, item);
					} else {
						return InventorySetSlot.create(version, locale, PESource.POCKET_CRAFTING_GRID_ADD, slot - 1, item);
					}
				} else if (slot <= 8) {
					return InventorySetSlot.create(version, locale, PESource.POCKET_ARMOR_EQUIPMENT, slot - 5, item);
				} else if (slot <= 35) {
					return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot, item);
				} else if (slot <= 44) {
					if (slot - 36 == invCache.getSelectedSlot()) {
						invCache.setItemInHand(item);
					}
					return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot - 36, item);
				} else if (slot == 45) {
					return InventorySetSlot.create(version, locale, PESource.POCKET_OFFHAND, 0, item);
				}
				break;
			}
			case BREWING_STAND: {
				if (slot == 3) {
					slot = 0;
				} else if (slot <= 2) {
					slot += 1;
				} else if (slot > 4) {
					return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, (slot >= 32 ? slot - 32 : slot + 4), item);
				}
				return InventorySetSlot.create(version, locale, winCache.getOpenedWindowId(), slot, item);
			}
			case ANVIL: {
				switch (slot) {
					case 0: {
						return InventorySetSlot.create(version, locale, PESource.POCKET_ANVIL_INPUT, 0, item);
					}
					case 1: {
						return InventorySetSlot.create(version, locale, PESource.POCKET_ANVIL_MATERIAL, 0, item);
					}
					case 2: {
						return InventorySetSlot.create(version, locale, PESource.POCKET_ANVIL_OUTPUT, 0, item);
					}
					default: {
						return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot >= 30 ? slot - 30 : slot + 6, item);
					}
				}
			}
			case ENCHANTMENT: {
				if (slot == 0) {
					invCache.getFakeEnchanting().setInputOutputStack(item);
					return invCache.getFakeEnchanting().updateInventory(cache, version);
				} else if (slot == 1) {
					invCache.getFakeEnchanting().setLapisStack(item);
					return invCache.getFakeEnchanting().updateInventory(cache, version);
				} else {
					return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot >= 29 ? slot - 29 : slot + 7, item);
				}
			}
			case BEACON: {
				if (slot == 0) {
					return InventorySetSlot.create(version, locale, PESource.POCKET_BEACON, 0, item);
				} else {
					return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot >= 28 ? slot - 28 : slot + 8, item);
				}
			}
			case CRAFTING: {
				if (slot == 0) {
					return InventorySetSlot.create(version, locale, PESource.POCKET_CRAFTING_RESULT, 0, item);
				} else if (slot < 10) {
					if (item.isNull()) {
						return InventorySetSlot.create(version, locale, PESource.POCKET_CRAFTING_GRID_REMOVE, slot - 1, item);
					} else {
						return InventorySetSlot.create(version, locale, PESource.POCKET_CRAFTING_GRID_ADD, slot - 1, item);
					}
				} else if (slot >= 37) {
					return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot - 37, item);
				} else {
					return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot - 1, item);
				}
			}
			case MERCHANT: {
				switch (slot) {
					case 0: {
						return InventorySetSlot.create(version, locale, PESource.POCKET_TRADE_INPUT_1, 0, item);
					}
					case 1: {
						return InventorySetSlot.create(version, locale, PESource.POCKET_TRADE_INPUT_2, 0, item);
					}
					case 2: {
						return InventorySetSlot.create(version, locale, PESource.POCKET_TRADE_OUTPUT, 0, item);
					}
					default: {
						return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot >= 30 ? slot - 30 : slot + 6, item);
					}
				}
			}
			default: {
				int wSlots = cache.getWindowCache().getOpenedWindowSlots();
				//Makes malformated inventory slot amounts to work. (Essentials's /invsee for example)
				if (wSlots > 16) {
					wSlots = wSlots / 9 * 9;
				}
				if (slot > wSlots) {
					slot -= wSlots;
					if (slot >= 27) {
						return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot - 27, item);
					} else {
						return InventorySetSlot.create(version, locale, PESource.POCKET_INVENTORY, slot + 9, item);
					}
				}
				return InventorySetSlot.create(version, locale, winCache.getOpenedWindowId(), slot, item);
			}
		}
		return null;
	}

	public static RecyclableArrayList<ClientBoundPacketData> remapClientBoundInventory(NetworkDataCache cache, ProtocolVersion version, NetworkItemStack[] items) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		String locale = cache.getAttributesCache().getLocale();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		WindowCache winCache = cache.getWindowCache();
		switch (winCache.getOpenedWindow()) {
			case PLAYER: {
				if (items.length < 46) {
					break;
				}
				NetworkItemStack[] peInvGridResult = new NetworkItemStack[1];
				NetworkItemStack[] peInvGrid = new NetworkItemStack[5];
				NetworkItemStack[] peArmor = new NetworkItemStack[4];
				NetworkItemStack[] peInventory = new NetworkItemStack[36];
				NetworkItemStack[] peOffhand = new NetworkItemStack[1];
				System.arraycopy(items,  0, peInvGridResult, 0 , 1);
				System.arraycopy(items,  1, peInvGrid, 		 0,  4);
				System.arraycopy(items,  5, peArmor, 		 0,  4);
				System.arraycopy(items, 36, peInventory, 	 0,  9);
				System.arraycopy(items,  9, peInventory, 	 9, 27);
				System.arraycopy(items, 45, peOffhand, 		 0,  1);
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_CRAFTING_RESULT, peInvGridResult));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_CRAFTING_GRID_ADD, peInvGrid));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_ARMOR_EQUIPMENT, peArmor));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_OFFHAND, peOffhand));
				break;
			}
			case BREWING_STAND: {
				NetworkItemStack[] brewingSlots = new NetworkItemStack[5];
				NetworkItemStack[] peInventory = new NetworkItemStack[36];
				System.arraycopy(items,  0, brewingSlots, 1,  3);
				System.arraycopy(items,  3, brewingSlots, 0,  1);
				System.arraycopy(items,  4, brewingSlots, 4,  1);
				System.arraycopy(items, 32, peInventory,  0,  9);
				System.arraycopy(items,  5, peInventory,  9, 27);
				packets.add(InventorySetItems.create(version, locale, winCache.getOpenedWindowId(), brewingSlots));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case ANVIL: {
				NetworkItemStack[] peAnvIn = new NetworkItemStack[1];
				NetworkItemStack[] peAnvMa = new NetworkItemStack[1];
				NetworkItemStack[] peAnvOu = new NetworkItemStack[1];
				NetworkItemStack[] peInventory = new NetworkItemStack[36];
				System.arraycopy(items,  0, peAnvIn, 	 0,  1);
				System.arraycopy(items,  1, peAnvMa, 	 0,  1);
				System.arraycopy(items,  2, peAnvOu,     0,  1);
				System.arraycopy(items, 30, peInventory, 0,  9);
				System.arraycopy(items,  3, peInventory, 9, 27);
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_ANVIL_INPUT, peAnvIn));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_ANVIL_MATERIAL, peAnvMa));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_ANVIL_OUTPUT, peAnvOu));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case ENCHANTMENT: { //Faked with hopper thingy, server sends the two slots though.
				NetworkItemStack[] peInventory = new NetworkItemStack[36];
				invCache.getFakeEnchanting().setInputOutputStack(items[0]);
				invCache.getFakeEnchanting().setLapisStack(items[1]);
				System.arraycopy(items, 29, peInventory, 0,  9);
				System.arraycopy(items,  2, peInventory, 9, 27);
				packets.add(invCache.getFakeEnchanting().updateInventory(cache, version));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case BEACON: {
				NetworkItemStack[] peBeaMa = new NetworkItemStack[1];
				NetworkItemStack[] peInventory = new NetworkItemStack[36];
				System.arraycopy(items,  0, peBeaMa, 	 0,  1);
				System.arraycopy(items, 28, peInventory, 0,  9);
				System.arraycopy(items,  1, peInventory, 9, 27);
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_BEACON, peBeaMa));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case CRAFTING: {
				NetworkItemStack[] craftingGridResult = new NetworkItemStack[1];
				NetworkItemStack[] craftingSlots = new NetworkItemStack[9];
				NetworkItemStack[] peInventory = new NetworkItemStack[36];
				System.arraycopy(items,  0, craftingGridResult, 0 , 1);
				System.arraycopy(items,  1, craftingSlots,	 	0,  9);
				System.arraycopy(items, 37, peInventory, 		0,  9);
				System.arraycopy(items, 10, peInventory, 		9, 27);
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_CRAFTING_RESULT, craftingGridResult));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_CRAFTING_GRID_ADD, craftingSlots));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case MERCHANT: {
				NetworkItemStack[] peTradeSA = new NetworkItemStack[1];
				NetworkItemStack[] peTradeSB = new NetworkItemStack[1];
				NetworkItemStack[] peTradeResult = new NetworkItemStack[1];
				NetworkItemStack[] peInventory = new NetworkItemStack[36];
				System.arraycopy(items,  0, peTradeSA, 		0 , 1);
				System.arraycopy(items,  1, peTradeSB, 		0,  1);
				System.arraycopy(items,  2, peTradeResult, 	0,  1);
				System.arraycopy(items, 30, peInventory, 	0,  9);
				System.arraycopy(items,  3, peInventory, 	9, 27);
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_TRADE_INPUT_1, peTradeSA));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_TRADE_INPUT_2, peTradeSB));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_TRADE_OUTPUT, peTradeResult));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case HORSE: {
				NetworkEntity horse = cache.getWatchedEntityCache().getWatchedEntity(winCache.getHorseId());
				if (horse != null) {
					//TODO: continue.
				}
				break;
			}
			default: {
				int wSlots = cache.getWindowCache().getOpenedWindowSlots();
				int peWSlots = wSlots;
				if (wSlots > 16) {
					//PE only has doublechest or single chest interface for high slots.
					wSlots = wSlots / 9 * 9;
					peWSlots = wSlots > 27 ? 54 : 27;
				}
				NetworkItemStack[] windowSlots = new NetworkItemStack[peWSlots];
				NetworkItemStack[] peInventory = new NetworkItemStack[36];
				System.arraycopy(items,		 	  0, 	windowSlots, 0, wSlots);
				System.arraycopy(items, wSlots + 27,	peInventory, 0,	 	 9);
				System.arraycopy(items, 	 wSlots, 	peInventory, 9, 	27);
				packets.add(InventorySetItems.create(version, locale, winCache.getOpenedWindowId(), windowSlots));
				packets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
		}
		return packets;
	}

	public static void remapServerboundSlot(NetworkDataCache cache, InvTransaction transaction) {
		WindowCache winCache = cache.getWindowCache();
		switch (winCache.getOpenedWindow()) {
			case PLAYER: {
				switch (transaction.getInventoryId()) {
					case PESource.POCKET_CRAFTING_RESULT: {
						transaction.setSlot(0);
						break;
					}
					case PESource.POCKET_CRAFTING_GRID_ADD:
					case PESource.POCKET_CRAFTING_GRID_REMOVE: {
						transaction.setSlot(transaction.getSlot() + 1);
						break;
					}
					case PESource.POCKET_ARMOR_EQUIPMENT: {
						transaction.setSlot(transaction.getSlot() + 5);
						break;
					}
					case PESource.POCKET_OFFHAND: {
						transaction.setSlot(45);
						break;
					}
					case PESource.POCKET_CRAFTING_GRID_USE_INGREDIENT: {
						transaction.setSlot(-333);
						break;
					}
					case PESource.POCKET_FAUX_DROP:
					case PESource.POCKET_CLICKED_SLOT:
					case PESource.POCKET_INVENTORY: {
						transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), 9, transaction.getSlot()));
						break;
					}
					default: {
						transaction.setSlot(InvTransaction.MISSING_REMAP);
						break;
					}
				}
				break;
			}
			case BREWING_STAND: {
				if (transaction.getInventoryId() == winCache.getOpenedWindowId()) {
					if (transaction.getSlot() == 0) {
						transaction.setSlot(3);
						break;
					} else if (transaction.getSlot() >= 1 && transaction.getSlot() <= 3) {
						transaction.setSlot(transaction.getSlot() - 1);
						break;
					}
				}
				transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), 5, transaction.getSlot()));
				break;
			}
			case ANVIL: {
				switch (transaction.getInventoryId()) {
					case PESource.POCKET_ANVIL_INPUT: {
						transaction.setSlot(0);
						break;
					}
					case PESource.POCKET_ANVIL_MATERIAL: {
						transaction.setSlot(1);
						break;
					}
					case PESource.POCKET_ANVIL_RESULT:
					case PESource.POCKET_ANVIL_OUTPUT: {
						transaction.setSlot(2);
						break;
					}
				}
				transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), 3, transaction.getSlot()));
				break;
			}
			case ENCHANTMENT: {
				//We fake enchanting with hoppers, but the server slots are still 0 and 1 for the inventory.
				transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), 2, transaction.getSlot()));
				break;
			}
			case BEACON: {
				if (transaction.getInventoryId() == PESource.POCKET_BEACON) {
					transaction.setSlot(0);
					break;
				} else {
					transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), 1, transaction.getSlot()));
					break;
				}
			}
			case CRAFTING: {
				switch (transaction.getInventoryId()) {
					case PESource.POCKET_CRAFTING_RESULT: {
						transaction.setSlot(0);
						break;
					}
					case PESource.POCKET_CRAFTING_GRID_ADD:
					case PESource.POCKET_CRAFTING_GRID_REMOVE: {
						transaction.setSlot(transaction.getSlot() + 1);
						break;
					}
					case PESource.POCKET_CRAFTING_GRID_USE_INGREDIENT: {
						transaction.setSlot(InvTransaction.TABLE);
						break;
					}
					case PESource.POCKET_FAUX_DROP:
					case PESource.POCKET_CLICKED_SLOT:
					case PESource.POCKET_INVENTORY: {
						transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), 10, transaction.getSlot()));
						break;
					}
					default: {
						transaction.setSlot(InvTransaction.MISSING_REMAP);
						break;
					}
				}
				break;
			}
			case MERCHANT: {
				switch (transaction.getInventoryId()) {
					case PESource.POCKET_TRADE_INPUT_1: {
						transaction.setSlot(0);
						break;
					}
					case PESource.POCKET_TRADE_INPUT_2: {
						transaction.setSlot(1);
						break;
					}
					case PESource.POCKET_TRADE_OUTPUT: {
						transaction.setSlot(2);
						break;
					}
					case PESource.POCKET_FAUX_DROP:
					case PESource.POCKET_CLICKED_SLOT:
					case PESource.POCKET_INVENTORY: {
						transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), 3, transaction.getSlot()));
						break;
					}
					default: {
						transaction.setSlot(InvTransaction.MISSING_REMAP);
						break;
					}
				}
				break;
			}
			case HORSE: {
				if (transaction.getInventoryId() == winCache.getOpenedWindowId()) {
					NetworkEntity horse = cache.getWatchedEntityCache().getWatchedEntity(winCache.getHorseId());
					if (horse != null) {
						switch (horse.getType()) {
							case DONKEY:
							case MULE: {
								if (transaction.getSlot() == 0) {
									break;
								} else {
									transaction.setSlot(transaction.getSlot() + 1);
									break;
								}
							}
							case LAMA: {
								transaction.setSlot(transaction.getSlot() + 1);
								break;
							}
							default: {
								transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), winCache.getOpenedWindowSlots(), transaction.getSlot()));
								break; //Fallthrough to defualt.
							}
						}
					}
				}
				break;
			}
			default: {
				int wSlots = winCache.getOpenedWindowSlots();
				if (wSlots > 16) {
					wSlots = wSlots / 9 * 9;
				}
				transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), wSlots, transaction.getSlot()));
			}
		}
	}

	private static int invSlotToContainerSlot(int peInventoryId, int start, int peSlot) {
		switch (peInventoryId) {
			case PESource.POCKET_CLICKED_SLOT: {
				return -1;
			}
			case PESource.POCKET_FAUX_DROP: {
				return peInventoryId;
			}
			case PESource.POCKET_INVENTORY: {
				if (peSlot < 9) {
					return peSlot + (27 + start);
				} else {
					return peSlot - (9 - start);
				}
			}
			default: {
				return peSlot;
			}
		}
	}

}
