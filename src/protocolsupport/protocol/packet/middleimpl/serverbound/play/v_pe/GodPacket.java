package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCreativeSetSlot;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleHeldSlot;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryTransaction;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.ArrayDequeMultiMap;
import protocolsupport.utils.ArrayDequeMultiMap.ChildDeque;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

/**
 * Lo and behold. The PE GodPacket. 
 * DKTAPPS THANK YOU FOR HELPING ME! - This would be insufferable without him.
 * 
 * This packet handles among other things
 *  - Using items (Right clicking)
 *  - Interacting (left and right clicking mob)
 *  - Releasing items (shoot bow, eat food)
 *  - Getting creative items
 *  - MANAGING INVENTORY
 *  
 *  Apart from managing inventories it is the usual packet deal,
 *  except that we have an extra layer of encapsulation.
 *  
 *  Managing inventories works very different in PE, so for now expect some hacky code.
 */
public class GodPacket extends ServerBoundMiddlePacket {
	
	//Complex Actions
	protected int actionId;
	protected int subTypeId = -1;
	protected ItemStackWrapper itemstack;
	protected int slot;
	protected Position position;
	protected float fromX, fromY, fromZ;
	protected float cX, cY, cZ;
	protected int face;
	protected int targetId;
	
	//Misc
	private static ItemStackWrapper AIR = ServerPlatform.get().getWrapperFactory().createItemStack(0);
	{
		AIR.setAmount(1);
	}

	public static void bug(String bugger) {
		System.out.println(bugger);
	}
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		System.out.println("NEEWWW GODPACKET! Hooraayy it's a god packet. Don't we all reeeaaally love godpackets? I do. IDDOODD I REALLY DO LOVE THEM. THTHISS IS THE BEST DAY IN MY LIFE.");
		actionId = VarNumberSerializer.readVarInt(clientdata);

		int transactionLength = VarNumberSerializer.readVarInt(clientdata);
		for(int i = 0; i < transactionLength; i++) {
			cache.getInfTransactions().cacheTransaction2(cache, InfTransaction.readFromStream(clientdata, cache.getLocale(), connection.getVersion()));
		}

		switch(actionId) {
			case ACTION_USE_ITEM: {
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				position = PositionSerializer.readPEPosition(clientdata);
				face = VarNumberSerializer.readSVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				cX = MiscSerializer.readLFloat(clientdata); cY = MiscSerializer.readLFloat(clientdata); cZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_INTERACT: {
				targetId = (int) VarNumberSerializer.readVarLong(clientdata);
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				cX = MiscSerializer.readLFloat(clientdata); cY = MiscSerializer.readLFloat(clientdata); cZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_RELEASE_ITEM: {
				subTypeId = VarNumberSerializer.readVarInt(clientdata);
				slot = VarNumberSerializer.readSVarInt(clientdata);
				itemstack = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true);
				fromX = MiscSerializer.readLFloat(clientdata); fromY = MiscSerializer.readLFloat(clientdata); fromZ = MiscSerializer.readLFloat(clientdata);
				break;
			}
			case ACTION_NORMAL:
			case ACTION_MISMATCH:
			default: {
				break;
			}
		}
		//BLEEHH!
		clientdata.readBytes(clientdata.readableBytes());
	}

	//Sources
	public static final int SOURCE_CONTAINER = 0;
	public static final int SOURCE_GLOBAL = 1;
	public static final int SOURCE_WORLD_INTERACTION = 2;
	public static final int SOURCE_CREATIVE = 3;
	public static final int SOURCE_TODO = 99999;
	//Actions
	public static final int ACTION_NORMAL = 0;
	public static final int ACTION_MISMATCH = 1;
	public static final int ACTION_USE_ITEM = 2;
	public static final int ACTION_INTERACT = 3;
	public static final int ACTION_RELEASE_ITEM = 4;
	//Action - Item Release
	public static final int RELEASE_RELEASE = 0;
	public static final int RELEASE_CONSUME = 1;
	//Action - Item Use
	public static final int USE_CLICK_BLOCK = 0;
	public static final int USE_CLICK_AIR = 1;
	public static final int USE_DIG_BLOCK = 2;
	//Action - Interact
	public static final int INTERACT_INTERACT = 0;
	public static final int INTERACT_ATTACK = 1;
	public static final int INTERACT_AT = 2;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
		switch(actionId) {
			case ACTION_USE_ITEM: {
				packets.add(MiddleHeldSlot.create(slot));
				switch(subTypeId) {
					case USE_CLICK_AIR:
						face = -1;
					case USE_CLICK_BLOCK: {
						cache.setClickedPosition(position);
						packets.add(MiddleBlockPlace.create(position, face, 0, cX, cY, cZ));
						break;
					}
					case USE_DIG_BLOCK: {
						if(cache.getGameMode() == GameMode.CREATIVE) { //instabreak
							packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.START_DIG, position, 0));
							packets.add(MiddleBlockDig.create(MiddleBlockDig.Action.FINISH_DIG, position, 0));
						}
						break;
					}
				}
				break;
			}
			case ACTION_INTERACT: {
				packets.add(MiddleHeldSlot.create(slot));
				switch(subTypeId) {
					case INTERACT_INTERACT: {
						NetworkEntity target = cache.getWatchedEntity(targetId);
						if(target == null || !target.isOfType(NetworkEntityType.ARMOR_STAND)) {
							packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT, null, 0));
						}
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), 0));
						break;
					}
					case INTERACT_ATTACK: {
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.ATTACK, null, 0));
						break;
					}
					case INTERACT_AT: {
						packets.add(MiddleUseEntity.create(targetId, MiddleUseEntity.Action.INTERACT_AT, new Vector(cX, cY, cZ), 0));
						break;
					}
				}
				break;
			}
			case ACTION_RELEASE_ITEM: {
				packets.add(MiddleHeldSlot.create(slot));
				//Special place packet.
				packets.add(MiddleBlockPlace.create(new Position(-1, 255, -1), -1, 0, cX, cY, cZ));
				break;
			}
			case ACTION_NORMAL: { //Normal inventory transaction.
				packets.addAll(cache.getInfTransactions().process2(cache));
				break;
			}
			case ACTION_MISMATCH:
			default: {
				break;
			}
		}
		return packets;
	}

	public static class InfTransaction {

		private int sourceId;
		private int inventoryId;
		private int action;
		private int slot;
		private ItemStackWrapper oldItem;
		private ItemStackWrapper newItem;

		private static InfTransaction readFromStream(ByteBuf from, String locale, ProtocolVersion version) {
			InfTransaction transaction = new InfTransaction();
			transaction.sourceId = VarNumberSerializer.readVarInt(from);
			switch(transaction.sourceId) {
				case SOURCE_CONTAINER: {
					transaction.inventoryId = VarNumberSerializer.readSVarInt(from);
					break;
				}
				case SOURCE_WORLD_INTERACTION: {
					transaction.inventoryId = PESource.POCKET_FAUX_DROP;
					transaction.action = VarNumberSerializer.readVarInt(from);
					break;
				}
				case SOURCE_TODO: {
					transaction.inventoryId = VarNumberSerializer.readSVarInt(from);
					transaction.action = 0;
					break;
				}
				case SOURCE_GLOBAL:
				case SOURCE_CREATIVE:
				default: {
					break;
				}
			}
			transaction.slot = VarNumberSerializer.readVarInt(from);
			transaction.oldItem = ItemStackSerializer.readItemStack(from, version, locale, true);
			transaction.newItem = ItemStackSerializer.readItemStack(from, version, locale, true);
			System.out.println("Inv transaction read: sId: " + transaction.sourceId + " wId: " + transaction.inventoryId + " action: " + transaction.action + " slot: " + transaction.slot 
					+ " oldItem: " + transaction.oldItem.toString() + " newItem: " + transaction.newItem.toString());
			return transaction;
		}
		
		public int getSourceId() {
			return sourceId;
		}


		public int getInventoryId() {
			return inventoryId;
		}


		public int getAction() {
			return action;
		}


		public int getSlot() {
			return slot;
		}
		
		public ItemStackWrapper getOldItem() {
			return oldItem;
		}


		public ItemStackWrapper getNewItem() {
			return newItem;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}
	
	public static class InfTransactions {
		Map<String, List<Integer>> surplus = new HashMap<>();
		Map<String, List<Integer>> deficit = new HashMap<>();
		List<ServerBoundPacketData> misc = new ArrayList<>();
		String cursorItem = getItemIdentity(AIR);
		int cursorValue = 0;
		int cursorSlotAmount = 0;
		int cursorSlot = 0;
		boolean hack1OrHack2 = false; //Ouch.
		
		public void clear() {
			bug("Clearing cache with surplus: " + surplus.toString() + " and deficit: " + deficit.toString());
			surplus.clear();
			deficit.clear();
			misc.clear();
			bug("Clearing cache with surplus2: " + surplus2.entrySet().size() + " and deficit2: " + deficit2.entrySet().size());
			surplus2.clear();
			deficit2.clear();
			misc2.clear();
		}
		
		public void cacheTransaction(NetworkDataCache cache, InfTransaction transaction) {
			int pcSlot = transformSlot(cache, transaction.getInventoryId(), transaction.getSlot());
			
			if(pcSlot == -666) {
				bug("Found the devil. Keeping him out!");
				return;
			}
			
			//Special cases
			if (cache.getGameMode() == GameMode.CREATIVE && cache.getOpenedWindow() == WindowType.PLAYER) {
				if(pcSlot != - 1) {
					bug("Creative transaction! Yay, sooo simple.");
					bug("Slot: " + (pcSlot == -999 ? -1 : pcSlot) + " item: " + getItemIdentity(transaction.getNewItem())); //Drop item is -1 for creative setSlot packet.
					misc.add(MiddleCreativeSetSlot.create(cache.getLocale(), (pcSlot == -999 ? -1 : pcSlot), transaction.getNewItem()));
				}
				return;
			}
			
			if (!transaction.getOldItem().equals(transaction.getNewItem())) {
				String oldId = getItemIdentity(transaction.getOldItem());
				String newId = getItemIdentity(transaction.getNewItem());
				if (oldId.equals(newId)) {
					int amount = transaction.getOldItem().getAmount() - transaction.getNewItem().getAmount();
					if (pcSlot == -1) {
						put(surplus, oldId, pcSlot, transaction.getOldItem().getAmount(), transaction.getOldItem().getAmount());
						put(deficit, oldId, pcSlot, 0, transaction.getNewItem().getAmount());
						hack1OrHack2 = true;
					} else {
						if (amount > 0) {
							put(surplus, oldId, pcSlot, transaction.getOldItem().getAmount(), amount);
							bug("adding surplus C: " + oldId + " : " + pcSlot + " : "+ transaction.getOldItem().getAmount() + " : " + amount);
						} else {
							put(deficit, oldId, pcSlot, transaction.getOldItem().getAmount(), amount * -1);
							bug("adding deficit C: " + oldId + " : " + pcSlot + " : " + transaction.getOldItem().getAmount() + " : " + amount * -1);
						}
					}
				} else {
					if (transaction.getOldItem().getType() != Material.AIR) {
						put(surplus, oldId, pcSlot, transaction.getOldItem().getAmount(), transaction.getOldItem().getAmount());
						bug("adding surplus N: " + oldId + " : " + pcSlot + " : " + transaction.getOldItem().getAmount() + " : " + transaction.getOldItem().getAmount());
					} if (transaction.getNewItem().getType() != Material.AIR) {
						put(deficit, newId, pcSlot, 0, transaction.getNewItem().getAmount());
						bug("adding deficit N: " + newId + " : " + pcSlot + " : " + transaction.getOldItem().getAmount() + " : " + transaction.getNewItem().getAmount());
					}
					if(pcSlot == -1) {
						hack1OrHack2 = false;
					}
				}
				if(pcSlot == -1) {
					if(transaction.getNewItem().getType() == Material.AIR) {
						cursorSlotAmount = 0;
					} else {
						cursorSlotAmount = transaction.getNewItem().getAmount();
					}
					cursorItem = newId;
				}
			}
		}
		
		private void sortSurplus() {
			surplus.replaceAll((iden, list) -> {
				Collections.sort(list, (s1, s2) -> {
					if(s1 >> 16 == -1) {
						return -1;
					} else if (s2 >> 16 == -1){
						return 1;
					} else {
						return 0;
					}
				});
				return list;
			});
		}
		
		private void sortDeficit() {
			deficit.replaceAll((iden, list) -> {
				Collections.sort(list, (s1, s2) -> {
					if(s1 >> 16 == -1) {
						return 1;
					} else if (s2 >> 16 == -1) {
						return -1;
					} else {
						return 0;
					}
				});
				return list;
			});
		}
		
		private void put(Map<String, List<Integer>> map, String id, int slot, int slotAmount, int amount) {
			bug("slot: " + slot + " slotAmount: " + slotAmount + " amount: " + amount);
			if(slot == -1) {
				bug("It's the CURSE-OR!");
				cursorValue++;
			}
			List<Integer> keys = map.get(id);
			if(keys == null) {
				keys = new ArrayList<Integer>();
				map.put(id, keys);
			}
			bug("Adding keyValue: " + Integer.toBinaryString(slot << 16 | slotAmount << 8 | amount));
			keys.add(slot << 16 | slotAmount << 8 | amount);
			bug("keysize: " + keys.size());
		}
		
		public RecyclableArrayList<ServerBoundPacketData> process(NetworkDataCache cache) {
			cache.lockInventory();
			if(cursorValue == 0 && cursorSlotAmount != 0) {
				bug("Pocket ONLY AND ONLY sends changes. RIP if cursor is already here, so HACK!");
				put(surplus, cursorItem, -1, cursorSlotAmount, cursorSlotAmount);
				put(deficit, cursorItem, -1, 0, cursorSlotAmount);
				hack1OrHack2 = true;
			}
			bug("Sorting lists...");
			sortSurplus(); sortDeficit();
			RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
			bug("Sending " + misc.size() + " misc packets...");
			packets.addAll(misc);
			misc.clear();
			bug("Processing surplus / deficit balance...");
			Iterator<Entry<String, List<Integer>>> deficitorator = deficit.entrySet().iterator();
			while (deficitorator.hasNext()) {
				Entry<String, List<Integer>> deficitEntry = deficitorator.next();
				List<Integer> surplusSlotValues = surplus.get(deficitEntry.getKey());
				bug("Deficits of " + deficitEntry.getKey() + "...");
				if(surplusSlotValues != null) {
					bug("Found surplus! (Where's the money lebowsky?!)");
					ListIterator<Integer> deficitSlotValueIterator = deficitEntry.getValue().listIterator();
					while (deficitSlotValueIterator.hasNext()) {
						Integer deficitSlotValue = deficitSlotValueIterator.next();
						bug("Local debt: " + Integer.toBinaryString(deficitSlotValue));
						int	deficitSlot = (deficitSlotValue >> 16), deficitSlotAmount = ((deficitSlotValue >> 8) & 0xFF), deficitAmount = (deficitSlotValue & 0xFF);
						bug("Deficit slot: " + deficitSlot + " deficitSlotAmount: " + deficitSlotAmount + " Debt amount: " + deficitAmount);
						ListIterator<Integer> surplusSlotValueIterator = surplusSlotValues.listIterator();
						while (surplusSlotValueIterator.hasNext()) {
							deficitSlotAmount = (deficitSlotValue >> 8) & 0xFF;
							deficitAmount = deficitSlotValue & 0xFF;
							bug("Rolling cashier..");
							Integer surplusSlotValue = surplusSlotValueIterator.next();
							bug("Surplus value: " + Integer.toBinaryString(surplusSlotValue));
							int	surplusSlot = (surplusSlotValue >> 16), surplusSlotAmount = ((surplusSlotValue >> 8) & 0xFF), surplusAmount = (surplusSlotValue & 0xFF);
							bug("Surplus slot: " + surplusSlot + " surplusSlotAmount: " + surplusSlotAmount + " surplus amount: " + surplusAmount);
							
							
							
							if(surplusSlot != -1) {
								bug("Left clicking (stealing) surplus...");
								packets.addAll(Click.LEFT.create(cache, surplusSlot));
							} else {
								bug("Surplus is in our own hand!");
							}
							if (deficitSlot != -1) {
								if(deficitAmount != surplusSlotAmount) {
									bug("Getting all we can get our hands on...");
									for (int i = 0; i < (deficitAmount < surplusAmount ? deficitAmount : surplusAmount); i++) {
										bug("right..");
										packets.addAll(Click.RIGHT.create(cache, deficitSlot));
										deficitSlotValue+=255; surplusSlotValue-=257;
									}
								} else {
									if(hack1OrHack2 || cursorValue < 2) {
										bug("Throwing it all on there");
										packets.addAll(Click.LEFT.create(cache, deficitSlot));	
									} else {
										bug("Something with the cursor to and from? :S");
									}
									surplusSlotValue -= (deficitAmount << 8 | deficitAmount);
									deficitSlotValue += ((deficitAmount << 8) - deficitAmount);
								}
							} else {
								if (deficitSlotAmount > 0 && hack1OrHack2 && deficitEntry.getKey() != getItemIdentity(AIR)) {
									bug("lefting again since we already have some in the cursor from another surplus.");
									packets.addAll(Click.LEFT.create(cache, surplusSlot));
								}
								bug("We took all...");
								int pAmount = deficitAmount < surplusAmount ? deficitAmount : surplusAmount;
								surplusSlotValue -= (pAmount << 8 | pAmount);
								deficitSlotValue += ((pAmount << 8) - pAmount);
								if(surplusSlotAmount > pAmount) {
									bug("More than we should actually..");
									for (int i  = 0; i < (surplusSlotAmount - pAmount); i ++) {
										bug("Right back!");
										packets.addAll(Click.RIGHT.create(cache, surplusSlot));
										//surplusSlotValue += 256;
									}
								}
								cursorSlot = surplusSlot;
							}
							if (surplusSlot != -1 && deficitSlot != -1 && surplusSlotAmount > 0) {
								bug("Putting the surplus back, cuz there's some left in the slot");
								packets.addAll(Click.LEFT.create(cache, surplusSlot));
							}
							
							
							
							if ((surplusSlotValue & 0xFF) == 0) {
								bug("The suitcase is empty!");
								surplusSlotValueIterator.remove();
								if(surplus.get(deficitEntry.getKey()).isEmpty()) {
									bug("Even the secret compartment!");
									surplus.remove(deficitEntry.getKey());
								}
							} else {
								surplusSlotValueIterator.set(surplusSlotValue);
							}
							if ((deficitSlotValue & 0xFF) == 0) {
								break;
							}
						}
						if ((deficitSlotValue & 0xFF) == 0) {
							bug("One debt less!");
							deficitSlotValueIterator.remove();
						} else {
							deficitSlotValueIterator.set(deficitSlotValue);
						}
					}
					if(deficitEntry.getValue().isEmpty()) {
						bug("All debt is payed! I AM REALLY FREE!!!");
						deficitorator.remove();
					}
				}
			}
			//Hack! :S
			cursorValue = 0;
			return packets;
		}
		
		private String getItemIdentity(ItemStackWrapper item) {
			return item.getTypeId() + "-" + item.getData() + "-" + item.getTag().toString();
		}
		
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
		
		ArrayDequeMultiMap<ItemStackWrapperKey, SlotWrapperValue> surplus2 = new ArrayDequeMultiMap<>();
		ArrayDequeMultiMap<ItemStackWrapperKey, SlotWrapperValue> deficit2 = new ArrayDequeMultiMap<>();
		RecyclableArrayList<ServerBoundPacketData> misc2 =  RecyclableArrayList.create();
		
		public void cacheTransaction2(NetworkDataCache cache, InfTransaction transaction) {
			int pcSlot = transformSlot(cache, transaction.getInventoryId(), transaction.getSlot());
			
			//Signifies death. We don't want unmapped slots to mess it all up.
			if (pcSlot == -666) {
				return;
			}
			
			//Creative transactions.
			if ((cache.getGameMode() == GameMode.CREATIVE) && (cache.getOpenedWindow() == WindowType.PLAYER)) {
				if (pcSlot != -1) {
					//Creative transaction use -1 not for cursor but throwing items, cursoritems are actually deleted on server.
					misc.add(MiddleCreativeSetSlot.create(cache.getLocale(), (pcSlot == -999 ? -1 : pcSlot), transaction.getNewItem()));
				}
				return;
			}
			
			ItemStackWrapperKey oldItem = new ItemStackWrapperKey(transaction.getOldItem());
			ItemStackWrapperKey newItem = new ItemStackWrapperKey(transaction.getNewItem());
			if (oldItem.equals(newItem)) {
				int money = transaction.getOldItem().getAmount() - transaction.getNewItem().getAmount();
				if((money > 0) && (pcSlot != -1)) {
					//If there's some money to spend, put it in the suitcase.
					surplus2.put(oldItem, new SlotWrapperValue(pcSlot, transaction.getOldItem().getAmount(), money));
				} else if (money < 0) {
					//Unless its the cursor (which is already in the system) add the debt.
					deficit2.put(oldItem, new SlotWrapperValue(pcSlot, transaction.getOldItem().getAmount(), -money), pcSlot == -1);	
				}
			} else {
				if((transaction.getOldItem().getType() != Material.AIR) && (pcSlot != -1)) {
					//Unless its the cursor (which is already in the system) add the debt.
					surplus2.put(oldItem, new SlotWrapperValue(pcSlot, transaction.getOldItem().getAmount(), transaction.getOldItem().getAmount()));
				}
				if(transaction.getNewItem().getType() != Material.AIR) {
					//If there's some money to spend, put it in the suitcase.
					deficit2.put(newItem, new SlotWrapperValue(pcSlot, 0, transaction.getNewItem().getAmount()), pcSlot == -1);
				}
			}
			
		}
		
		public RecyclableArrayList<ServerBoundPacketData> process2(NetworkDataCache cache) {
			RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
			//Since pocket simulates most things clientside, 
			//we stop any incoming inventory traffic when processing slots
			//until the manual override kicks in.
			cache.lockInventory();
			
			//Send misc data. (Creative inventory, anvils, etc)
			if(!misc2.isEmpty()) {
				packets.addAll(misc2);
				misc2.recycle();
			}
			
			//Match surpluses with defcitis.
			deficit2.cycleUp((item, deficits) -> {
				bug("Deficits: " + item.toString() + " size: " + deficits.size());
				ChildDeque<SlotWrapperValue> surpluses = surplus2.get(item);
				if(surpluses != null) {
					bug("surplusses: " + item.toString() + " size: " + surpluses.size());
					deficits.cycleUp(deficit -> {
						bug("Cycling up from " + deficit.slot());
						surpluses.cycleDown(surplus -> {
							bug("Cycling down from " + surplus.slot());
							//We want to get all we can, but not more than we need.
							int toPay = deficit.amount() < surplus.amount() ? deficit.amount() : surplus.amount();
							bug("Needing to pay: " + toPay);
							if (!surplus.isCursor()) {
								//Unless the surplus is already in the cursor, we need to get it.
								packets.add(Click.LEFT.create(cache, surplus.slot(), item.get(surplus.slotAmount())));
							}
							if (deficit.isCursor()) {
								if (deficit.hasStack()) {
									//We already had contents in the cursor, so just one left click won't cut it.
									packets.add(Click.LEFT.create(cache, surplus.slot(), item.get(surplus.slotAmount())));
								}
								//Put back the items from the cursor that we don't want.
								for (int i = 0; i < (surplus.slotAmount() - toPay); i++) {
									packets.add(Click.RIGHT.create(cache, surplus.slot(), (i == 0) ? AIR : item.get(i)));
								}
							} else {
								if (deficit.amount() == surplus.slotAmount()) {
									bug("Swap? " + surplus.isCursor() + " - " + deficit.slot() + " - " + surplus2.getVeryLast().slot());
									if (!((surplus.isCursor()) && (deficit.slot() == surplus2.getVeryLast().slot()))) {
										//Unless we're swapping (then only one left click is send on next deficit), we can payout the stack in full (left-click)
										packets.add(Click.LEFT.create(cache, deficit.slot(), (!deficit.hasStack()) ? AIR : item.get(deficit.slotAmount())));
									}
								} else {
									//We need to pay what we need to pay.
									for (int i = 0; i < toPay; i++) {
										packets.add(Click.RIGHT.create(cache, deficit.slot(), ((deficit.slotAmount() + i) == 0) ? AIR : item.get(deficit.slotAmount() + i)));
									}
								}
							}
							//Payout the trackers.
							surplus.pay(toPay); 
							deficit.receive(toPay);
							//Last but not least, put back our access surplus and or containing stack.
							if(!surplus.isCursor() && !deficit.isCursor() && surplus.hasStack()) {
								packets.add(Click.LEFT.create(cache, surplus.slot(), AIR));
							}
							return surplus.isEmpty();
						});
						if(surpluses.isEmpty()) {
							surplus2.remove(item);
						}
						//If there's still something in the cursor we want it to be accessed next round.
						if(deficit.isCursor() && deficit.hasStack()) {
							deficit.setAmount(deficit.slotAmount());
							surplus2.put(item, deficit, true);
							return true;
						}
						return deficit.isEmpty();
					});
				}
				return deficits.isEmpty();
			});
			
			return packets;
		}
		
	}
	
	
	
	
	protected static class SlotWrapperValue {
		
		private int slotValue;
		
		public SlotWrapperValue(int slot, int slotAmount, int amount) {
			slotValue = (slot << 16 | slotAmount << 8 | amount);
		}
		
		public int slot() {
			return slotValue >> 16;
		}
		
		public int slotAmount() {
			return (slotValue >> 8) & 0xFF;
		}
		
		public int amount() {
			return slotValue & 0xFF;
		}
		
		public void setAmount(int amount) {
			slotValue = (slotValue & ~(0xFF)) | amount;
		}
		
		public void pay(int amount) {
			slotValue -= (amount << 8 | amount);
		}
		
		public void receive(int amount) {
			slotValue += ((amount << 8) - amount);
		}
		
		public boolean hasStack() {
			return slotAmount() != 0;
		}
		
		public boolean isCursor() {
			return slot() == -1;
		}
		
		public boolean isEmpty() {
			return amount() == 0;
		}
		
	}
	
	protected static class ItemStackWrapperKey {
		
		private ItemStackWrapper keyItem;
		private int hash = 0;
		
		public ItemStackWrapperKey(ItemStackWrapper keyItem) {
			this.keyItem = keyItem;
			hash = 41 * hash + keyItem.getTypeId();
			hash = 41 * hash + keyItem.getData();
			hash = 41 * hash + keyItem.getTag().hashCode();
		}
		
		@Deprecated
		public ItemStackWrapper getKeyItem() {
			return keyItem;
		}
		
		public ItemStackWrapper get(int amount) {
			ItemStackWrapper item = getKeyItem().cloneItemStack();
			item.setAmount(amount);
			return item;
		}
		
		@Override
		public int hashCode() {
			return hash;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof ItemStackWrapperKey) {
				ItemStackWrapperKey wrapper = (ItemStackWrapperKey) o;
				return (wrapper.getKeyItem().getTypeId() == this.getKeyItem().getTypeId()) &&
						(wrapper.getKeyItem().getData() == this.getKeyItem().getData()) &&
						(wrapper.getKeyItem().getTag().equals( this.getKeyItem().getTag()));
			}
			return false;
			
		}
		
		@Override
		public String toString() {
			return keyItem.toString();
		}
		
	}
	
	private static int transformSlot(NetworkDataCache cache, int peInventoryId, int peSlot) {
		switch(cache.getOpenedWindow()) {
			case PLAYER: {
				switch(peInventoryId) {
					case PESource.POCKET_CLICKED_SLOT: {
						return -1;
					}
					case PESource.POCKET_CRAFTING_RESULT: {
						return 0;
					}
					case PESource.POCKET_CRAFTING_GRID_ADD:
					case PESource.POCKET_CRAFTING_GRID_REMOVE: {
						return peSlot + 1;
					}
					case PESource.POCKET_ARMOR_EQUIPMENT: {
						return peSlot + 5;
					}
					case PESource.POCKET_INVENTORY: {
						if (peSlot < 9) {
							return peSlot + 36;
						} else {
							return peSlot;
						}
					}
					case PESource.POCKET_OFFHAND: {
						return 45;
					}
					case PESource.POCKET_FAUX_DROP: {
						return peInventoryId;
					}
					default: {
						return -666;
					}
				}
			}
			case CRAFTING_TABLE: {
				return invSlotToContainerSlot(peInventoryId, 10, peSlot);
			}
			default: {
				return invSlotToContainerSlot(peInventoryId, cache.getOpenedWindowSlots(), peSlot);
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
	
	protected enum Click {
		LEFT	(0, 0),
		RIGHT	(0, 1);
		
		private final int mode;
		private final int button;
		
		Click(int mode, int button) {
			this.mode = mode;
			this.button = button;
		}
		
		public ServerBoundPacketData create(NetworkDataCache cache, int slot, ItemStackWrapper item) {
			return MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), slot, button, cache.getActionNumber(), mode, item);
		}
		
		public RecyclableArrayList<ServerBoundPacketData> create(NetworkDataCache cache, int slot) {
			RecyclableArrayList<ServerBoundPacketData> clickPackets = RecyclableArrayList.create();
			int actionNumber = cache.getActionNumber();
			clickPackets.add(MiddleInventoryClick.create(cache.getLocale(), cache.getOpenedWindowId(), slot, button, actionNumber, mode, AIR));
			clickPackets.add(MiddleInventoryTransaction.create(cache.getOpenedWindowId(), actionNumber, false)); //Instant apology :D
			return clickPackets;
		}
		
	}

}
