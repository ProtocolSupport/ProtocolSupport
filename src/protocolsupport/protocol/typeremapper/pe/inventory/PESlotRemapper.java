package protocolsupport.protocol.typeremapper.pe.inventory;

import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.GodPacket.InvTransaction;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.storage.netcache.WindowCache;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;

public class PESlotRemapper {
	
	public static void remapServerboundSlot(NetworkDataCache cache, InvTransaction transaction) {
		WindowCache winCache = cache.getWindowCache();
		switch(winCache.getOpenedWindow()) {
			case PLAYER: {
				switch(transaction.getInventoryId()) {
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
					case PESource.POCKET_CRAFTING_GRID_USE_INGREDIENT:  {
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
			case BREWING: {
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
				switch(transaction.getInventoryId()) {
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
			case ENCHANT: {
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
			case CRAFTING_TABLE: {
				switch(transaction.getInventoryId()) {
					case PESource.POCKET_CRAFTING_RESULT: {
						transaction.setSlot(0);
						break;
					}
					case PESource.POCKET_CRAFTING_GRID_ADD:
					case PESource.POCKET_CRAFTING_GRID_REMOVE: {
						transaction.setSlot(transaction.getSlot() + 1);
						break;
					}
					case PESource.POCKET_CRAFTING_GRID_USE_INGREDIENT:  {
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
			case VILLAGER: {
				switch(transaction.getInventoryId()) {
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
						switch(horse.getType()) {
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
				if (wSlots > 16) { wSlots = wSlots / 9 * 9; }
				transaction.setSlot(invSlotToContainerSlot(transaction.getInventoryId(), wSlots, transaction.getSlot()));
			}
		}
	}
	
	private static int invSlotToContainerSlot(int peInventoryId, int start, int peSlot) {
		switch(peInventoryId) {
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
